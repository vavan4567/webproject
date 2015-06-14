/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.neo4j.cypher.internal.compiler.v2_1.executionplan.ExecutionWorkflowBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author andrew
 */
@RestController
public class Controller {
    ArrayList<PdfUrlCollectionItem> url_pdf = new ArrayList<PdfUrlCollectionItem>();
  
    @RequestMapping("/get_url")
    public String greeting(@RequestParam(value="_id") int id) {

            for(PdfUrlCollectionItem item : url_pdf)
            {
                if(item.association_id == id) return item.url;
            }
            PdfUrlCollectionItem it = new PdfUrlCollectionItem(id);
            url_pdf.add(it);
            return it.url;
    }
    @RequestMapping("/file")
    public void getFile(@RequestParam(value="_code") long id,HttpServletResponse response) {
        
        response.setContentType("application/pdf");
            for(PdfUrlCollectionItem item : url_pdf)
            {
                if(item.code==id)   
                {
                    id = item.association_id;
                    System.out.println(""+id);
                    try{
                        Association a = null;
                        for(int i = 0;i<Application.Associations.size();i++)
                        {
                            if(Application.Associations.get(i).getId()==id)
                            {
                                a = Application.Associations.get(i);

                            }
                        }
                        if(a==null) response.sendError(404);
                        else{
                              System.out.println("Create document");
                              create_Document("file"+id, a);
                              String s = new String("/home/vova/NetBeansProjects/Neo4jProject/file"+id+"-signed.pdf");
                              File f = new File(s);
                              InputStream is = new FileInputStream(f);
                              // copy it to response's OutputStream
                              IOUtils.copy(is, response.getOutputStream());
                              response.flushBuffer();
                        }

                      } catch (Exception ex) {
                          System.out.println(ex.getMessage());
                          ex.printStackTrace();
                      }
                }
            }
        
            
          
        
    }
    
    public void create_Document(String path,Association a) throws Exception
    {
         Document document = new Document();
         FileOutputStream outputStream = new FileOutputStream(path+".pdf");
        // step 2
        PdfWriter.getInstance(document,outputStream);

        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("----Lab 6 Generate signed PDF----"));
        document.add(new Paragraph(a.toString()));
        // step 5
                
        document.addAuthor("Machekhin Volodymyr");
        document.close();
        outputStream.flush();
        outputStream.close();
        JPdfSign sign = new JPdfSign();
        sign.generate_signed_pdf("/home/vova/mykey.p12",path+".pdf",path+"-signed.pdf");
        
        
    }
    public class PdfUrlCollectionItem
    {
        public String url;
        public int code;
        public int association_id;
        public PdfUrlCollectionItem(int id)
        {
            association_id = id;
            code = new Random().nextInt(1000000000);
            url = "http://127.0.0.1:8090/file?_code="+code;
        }
    }
}
