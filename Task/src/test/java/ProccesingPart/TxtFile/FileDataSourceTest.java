package ProccesingPart.TxtFile;

import ProccesingPart.CalculParse.MatchParser;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDataSourceTest extends TestCase {
    private MatchParser p;
    private File in;
    private File out;
    private String pathIn;
    private String pathOut;
    @Before
    public void setUp(){
        p = new MatchParser();
        pathIn = "C:\\Users\\gleb\\Desktop\\Cross_Cut_project-main\\src\\test\\java\\ProccesingPart\\TxtFile\\input.txt";
        in = new File(pathIn);
        pathOut = "C:\\Users\\gleb\\Desktop\\Cross_Cut_project-main\\src\\test\\java\\ProccesingPart\\TxtFile\\input.txt";
        out = new File(pathOut);
        try (FileOutputStream fos = new FileOutputStream(pathIn)) {
            boolean created = in.createNewFile();
            fos.write("2+2 = 4.0 \n hello, people".getBytes(), 0, "2+2 = 4.0 \n hello, people".length());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Test
    public void testWriteData() throws Exception {
        FileDataSource outputFile = new FileDataSource(pathOut);
        FileDataSource inputFile = new FileDataSource(pathIn);
        outputFile.writeData(p.Expression(inputFile.readData()));
        Assert.assertEquals("4.0 = 4.0 \n hello, people", outputFile.readData());
    }
    @Test
    public void testReadData() throws FileNotFoundException {
        FileDataSource inputFile = new FileDataSource(pathIn);
        Assert.assertEquals("2+2 = 4.0 \n hello, people", inputFile.readData());
    }
    @Test
    public void testCompressiveEncryption() throws Exception {
        DataSourceDecorator encoded_output = new Compression(
                new Encryption(
                        new FileDataSource(pathOut)));
        FileDataSource inputFile = new FileDataSource(pathIn);
        encoded_output.writeData(p.Expression(inputFile.readData()));
        Assert.assertEquals("4.0 = 4.0 \n hello, people", encoded_output.readData());
    }
    @Test
    public void testCompEncCompEnc() throws Exception {
        DataSourceDecorator encoded_output = new Compression(
                new Encryption(
                        new Compression(
                                new Encryption(
                                        new FileDataSource(pathOut)))));
        FileDataSource inputFile = new FileDataSource(pathIn);
        //String s = p.Expression(inputFile.readData());
        encoded_output.writeData(p.Expression(inputFile.readData()));
        Assert.assertEquals("4.0 = 4.0 \n hello, people", encoded_output.readData());
    }
    @After
    public void clear(){
        boolean delIn = in.delete();
        boolean delOut = out.delete();
    }
}