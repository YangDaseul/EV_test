package com.genesis.apps.comm.util.crypt.ecies;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;


class ECUtils
{
    static AsymmetricKeyParameter generatePublicKeyParameter(
            PublicKey key)
        throws InvalidKeyException
    {
    	if((key instanceof BCECPublicKey)) {
    		//((BCECPublicKey) key).getParameters()
    		// ajc
    		try {
    			BCECPublicKey temp = 	(BCECPublicKey)key;
        		Method method = temp.getClass().getDeclaredMethod("engineGetKeyParameters");
        		method.setAccessible(true);
        		ECPublicKeyParameters r = (ECPublicKeyParameters) method.invoke(temp);
        		return r;
			} catch (Exception e) {
				e.printStackTrace();
			}
    		
    		throw new InvalidKeyException("");
    		// ajc
    	} else {
    		return ECUtil.generatePublicKeyParameter(key);
    	}
//        return (key instanceof BCECPublicKey) ? /*((BCECPublicKey)key).engineGetKeyParameters()*/ throw new InvalidKeyException("") : ECUtil.generatePublicKeyParameter(key);
    }

    static X9ECParameters getDomainParametersFromGenSpec(ECGenParameterSpec genSpec)
    {
        return getDomainParametersFromName(genSpec.getName());
    }

    static X9ECParameters getDomainParametersFromName(String curveName)
    {
        X9ECParameters domainParameters;
        try
        {
            if (curveName.charAt(0) >= '0' && curveName.charAt(0) <= '2')
            {
                ASN1ObjectIdentifier oidID = new ASN1ObjectIdentifier(curveName);
                domainParameters = ECUtil.getNamedCurveByOid(oidID);
            }
            else
            {
                if (curveName.indexOf(' ') > 0)
                {
                    curveName = curveName.substring(curveName.indexOf(' ') + 1);
                    domainParameters = ECUtil.getNamedCurveByName(curveName);
                }
                else
                {
                    domainParameters = ECUtil.getNamedCurveByName(curveName);
                }
            }
        }
        catch (IllegalArgumentException ex)
        {
            domainParameters = ECUtil.getNamedCurveByName(curveName);
        }
        return domainParameters;
    }

    static X962Parameters getDomainParametersFromName(ECParameterSpec ecSpec, boolean withCompression)
    {
        X962Parameters params;

        if (ecSpec instanceof ECNamedCurveSpec)
        {
            ASN1ObjectIdentifier curveOid = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)ecSpec).getName());
            if (curveOid == null)
            {
                curveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec)ecSpec).getName());
            }
            params = new X962Parameters(curveOid);
        }
        else if (ecSpec == null)
        {
            params = new X962Parameters(DERNull.INSTANCE);
        }
        else
        {
            ECCurve curve = EC5Util.convertCurve(ecSpec.getCurve());

            X9ECParameters ecP = new X9ECParameters(
                curve,
                EC5Util.convertPoint(curve, ecSpec.getGenerator(), withCompression),
                ecSpec.getOrder(),
                BigInteger.valueOf(ecSpec.getCofactor()),
                ecSpec.getCurve().getSeed());

            params = new X962Parameters(ecP);
        }

        return params;
    }
}
