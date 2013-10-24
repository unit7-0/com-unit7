package com.unit7.study.cryptography.labs.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.unit7.study.cryptography.labs.exceptions.UnspecifiedField;
import com.unit7.study.cryptography.labs.lab2.CoderInfo;
import com.unit7.study.cryptography.labs.lab2.CoderInfoFactoryImpl;
import com.unit7.study.cryptography.labs.lab2.CodingInputStream;
import com.unit7.study.cryptography.labs.lab2.DecodingInputStream;
import com.unit7.study.cryptography.labs.lab2.IntOutputStream;
import com.unit7.study.cryptography.labs.lab2.RSACoder;
import com.unit7.study.cryptography.labs.lab2.Rewriter;
import com.unit7.study.cryptography.labs.lab3.ElGamalSignAlgorithm;
import com.unit7.study.cryptography.labs.lab3.RSASignAlgorithm;
import com.unit7.study.cryptography.labs.lab3.SignProcessor;
import com.unit7.study.cryptography.labs.lab3.SignedData;
import com.unit7.study.cryptography.labs.lab3.Signer;
import com.unit7.study.cryptography.labs.lab3.SignerImpl;
import com.unit7.study.cryptography.tools.CoderInfoFactory;
import com.unit7.study.cryptography.tools.CryptoData;
import com.unit7.study.cryptography.tools.Pair;
import com.unit7.study.cryptography.tools.Utils;

/**
 * Необходимо подписать файл. Записать файл и подпись либо вместе, либо отдельно
 * Затем считать файл и подпись и убедиться в достоверности данных
 */
public class Lab3Tests {
    
    public void rsignature() {
        RSACoder coder = new RSACoder();
        coder.setDb(coder.getD());
        coder.setNb(coder.getN());

        MessageDigest digester = null;
        InputStream in = null;
        byte[] data = null;

        try {
            digester = MessageDigest.getInstance("MD5");
            in = new FileInputStream(fileIn);
            data = new byte[in.available()];
            in.read(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Assert.fail();
        }

        // signing
        byte[] digest = digester.digest(data);
        in = new ByteArrayInputStream(digest);

        CodingInputStream codingIn = new CodingInputStream(in, coder);
        OutputStream out = new ByteArrayOutputStream();

        OutputStream output = new IntOutputStream(out);
        Rewriter rewriter = new Rewriter(codingIn, output);

        try {
            rewriter.rewrite();
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }

        // 128 bit MD5-hash -> 16 bytes -> coded 16 * 4 = 64 first bytes of file
        // is signature

        in = new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());

        try {
            out = new FileOutputStream(signedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }

        // first we write to file the signature
        rewriter.setIn(in);
        rewriter.setOut(out);

        try {
            rewriter.rewrite();
        } catch (IOException e1) {
            e1.printStackTrace();
            Assert.fail();
        }

        // and second we write the our file
        try {
            in = new FileInputStream(fileIn);
            out = new FileOutputStream(signedFile, true);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            Assert.fail();
        }

        rewriter.setIn(in);
        rewriter.setOut(out);

        try {
            rewriter.rewrite();
        } catch (IOException e1) {
            e1.printStackTrace();
            Assert.fail();
        }

        Assert.assertTrue(checkSignature(coder, digester));
    }

    private boolean checkSignature(CoderInfo coder, MessageDigest digester) {
        // read sign from file and file contained data
        // then check hash data == decoded sign
        InputStream in = null;
        OutputStream out = null;

        byte[] digest = null;
        byte[] data = null;

        try {
            in = new FileInputStream(signedFile);
            digest = new byte[16 * 4];
            data = new byte[in.available() - digest.length];
            in.read(digest);
            in.read(data);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        in = new ByteArrayInputStream(digest);
        out = new ByteArrayOutputStream();

        // decoding hash
        DecodingInputStream decIn = new DecodingInputStream(in, coder);
        Rewriter rewriter = new Rewriter(decIn, out);

        try {
            rewriter.rewrite();
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        digest = ((ByteArrayOutputStream) out).toByteArray();
        byte[] newDigest = digester.digest(data);

        // check equals
        return Arrays.equals(digest, newDigest);
    }

    public void signAndVerify(Signer signer) {
        SignProcessor processor = new SignProcessor(signer);

        byte[] message = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(fileIn);
            message = new byte[in.available()];
            in.read(message);
        } catch (FileNotFoundException e1) {
            Assert.fail(e1.getLocalizedMessage());
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        }

        SignedData signedData = processor.sign(message);
        ObjectInputStream objIn = null;
        ObjectOutputStream objOut = null;
        
        // write object
        try {
            out = new FileOutputStream(signedFile);
            objOut = new ObjectOutputStream(out);
            objOut.writeObject(signedData);
        } catch (FileNotFoundException e1) {
            Assert.fail(e1.getLocalizedMessage());
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        }

        // read object
        try {
            in = new FileInputStream(signedFile);
            objIn = new ObjectInputStream(in);
            signedData = (SignedData) objIn.readObject();
        } catch (FileNotFoundException e1) {
            Assert.fail(e1.getLocalizedMessage());
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            Assert.fail(e.getLocalizedMessage());
        }
        
        // verifiy
        try {
            Assert.assertTrue(processor.verify(signedData));
        } catch (NoSuchAlgorithmException | UnspecifiedField e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void gostSignature() {
        
    }
    
    @Test
    public void rsaSignature() {
        Signer signer = new SignerImpl(new RSASignAlgorithm());
        signAndVerify(signer);
    }
    
    @Test
    public void ElGamalSignature() {
        Signer signer = new SignerImpl(new ElGamalSignAlgorithm());
        signAndVerify(signer);
    }

    @Before
    public void prepare() {
        coderInfoFactory = new CoderInfoFactoryImpl();
    }

    private CoderInfoFactory coderInfoFactory;
    private String fileIn = "file.txt";
    private String signedFile = "signed";
    private String designedFile = "designed.txt";
}
