package com.llf.utils;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

import cn.hutool.crypto.ECKeyUtil;
import cn.hutool.crypto.SecureUtil;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class SM2Util {
	
    /**
     * SM2加密算法，然后转换为16进制字符串
     * @param publicKey     公钥
     * @param data          明文数据
     * @return
     */
    public static String encrypt(String data,String publicKey) {
        // 获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        // 构造ECC算法参数，曲线方程、椭圆曲线G点、大整数N
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());
        //提取公钥点
        ECPoint pukPoint = sm2ECParameters.getCurve().decodePoint(Hex.decode(publicKey));
        // 公钥前面的02或者03表示是压缩公钥，04表示未压缩公钥, 04的时候，可以去掉前面的04
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(pukPoint, domainParameters);
 
        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        // 设置sm2为加密模式
        sm2Engine.init(true, new ParametersWithRandom(publicKeyParameters, new SecureRandom()));
 
        byte[] arrayOfBytes = null;
        try {
            byte[] in = data.getBytes();
            arrayOfBytes = sm2Engine.processBlock(in, 0, in.length);
        } catch (Exception e) {
            System.out.println("SM2加密时出现异常:"+e.getMessage());
        }
        return Hex.toHexString(arrayOfBytes);
 
    }
 
    /**
     * SM2解密算法
     * @param privateKey        私钥
     * @param cipherData        密文数据
     * @return
     */
    public static String decrypt(String cipherData,String privateKey) {
        // 使用BC库加解密时密文以04开头，传入的密文前面没有04则补上
        if (!cipherData.startsWith("04")){
            cipherData = "04" + cipherData;
        }
        byte[] cipherDataByte = Hex.decode(cipherData);
        BigInteger privateKeyD = new BigInteger(privateKey, 16);
        //获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        //构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());
        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyD, domainParameters);
 
        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        // 设置sm2为解密模式
        sm2Engine.init(false, privateKeyParameters);
 
        String result = "";
        try {
            byte[] arrayOfBytes = sm2Engine.processBlock(cipherDataByte, 0, cipherDataByte.length);
            return new String(arrayOfBytes);
        } catch (Exception e) {
            System.out.println("SM2解密时出现异常:"+e.getMessage());
        }
        return result;
    }
 
     //生成密钥
    public static void createKey() throws Exception{
        ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
        // 获取一个椭圆曲线类型的密钥对生成器
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
        // 使用SM2参数初始化生成器
        kpg.initialize(sm2Spec);
        // 获取密钥对
        KeyPair keyPair = kpg.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        BCECPublicKey p=(BCECPublicKey)publicKey;
        System.out.println("publicKey："+Hex.toHexString(p.getQ().getEncoded(false)));
        PrivateKey privateKey = keyPair.getPrivate();
        BCECPrivateKey s=(BCECPrivateKey)privateKey;
        System.out.println("privateKey："+Hex.toHexString(s.getD().toByteArray()));
    }
    
    //签名
    public static String sign(String privateKey, String content) throws CryptoException, NoSuchAlgorithmException {
        ECPrivateKeyParameters privateKeyParams = ECKeyUtil.decodePrivateKeyParams(SecureUtil.decode(privateKey));
        //待签名内容转为字节数组
        byte[] message = content.getBytes();
        //获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        //构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(),
                sm2ECParameters.getG(), sm2ECParameters.getN());
 
        BigInteger privateKeyD = new BigInteger(privateKeyParams.getD().toString(16), 16);
        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyD, domainParameters);
 
        //创建签名实例
        SM2Signer sm2Signer = new SM2Signer();
        //ID默认值:1234567812345678
        sm2Signer.init(true, new ParametersWithID(new ParametersWithRandom(privateKeyParameters, SecureRandom.getInstance("SHA1PRNG")), Strings.toByteArray("1234567812345678")));
        sm2Signer.update(message, 0, message.length);
        byte[] signBytes = sm2Signer.generateSignature();
        String sign = Hex.toHexString(signBytes);
        return sign;
    }
 
 
    //验签
    public static boolean verify(String content, String publicKey, String sign)  {
        ECPublicKeyParameters publicKeyKeyParams = ECKeyUtil.decodePublicKeyParams(SecureUtil.decode(publicKey));
        //待签名内容
        byte[] message = content.getBytes();
        byte[] signData = Hex.decode(sign);
        // 获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        // 构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(),
                sm2ECParameters.getG(),
                sm2ECParameters.getN());
        //提取公钥点
        ECPoint pukPoint = sm2ECParameters.getCurve().decodePoint(Hex.decode(new String(Hex.encode(publicKeyKeyParams.getQ().getEncoded(true)))));
        // 公钥前面的02或者03表示是压缩公钥，04表示未压缩公钥, 04的时候，可以去掉前面的04
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(pukPoint, domainParameters);
        //创建签名实例
        SM2Signer sm2Signer = new SM2Signer();
        //ID默认值:1234567812345678
        ParametersWithID parametersWithID = new ParametersWithID(publicKeyParameters, Strings.toByteArray("1234567812345678"));
        sm2Signer.init(false, parametersWithID);
        sm2Signer.update(message, 0, message.length);
        //验证签名结果
        boolean verify = sm2Signer.verifySignature(signData);
        return verify;
    }
    
    public static void main(String[] args) {
    	try {
    		String content = "我是程序员";
    		System.out.println("数据："+ content);
    		String signData = sign("7b1a6e9e72586c8674313049a5e366816d8c29ff2728e142e3b423f4c63167d7", content);
    		System.out.println("加签后的数据："+ signData);
    		boolean result = verify("我是程序员", "0443cbfde3ad0308294ce1752cbc3345d7692ee813a83672a9e93fb80355b96ae4b941fe100336f3c03fb2471c4242659edb275cb928b6f8bfb16312c8a0d2db37", signData);
    		System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}