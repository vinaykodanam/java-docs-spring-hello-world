package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {
 public String Result = "";
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	
	@RequestMapping("/")
	String sayHello()   {
      
        String FOLDER_PATH = "src\\main\\java\\com\\example\\FilestoRead\\";        
        Path folderPath = Paths.get(FOLDER_PATH);

        // retrieve a list of the files in the folder
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath)) {
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }
        } catch (IOException ex) {
            System.err.println("Error reading files");
            ex.printStackTrace();
        }

        // go through the list of files
        for (String file : fileNames) {
            try {
                // put the file's name and its content into the data structure
                //List<String> lines = Files.readAllLines(folderPath.resolve(file));
                ProcessBuilder pb = new ProcessBuilder(file);
           
				try {
					Process p = pb.start();
					StringBuilder str = new StringBuilder();
					InputStreamReader isr = new InputStreamReader(p.getInputStream());
					BufferedReader br = new BufferedReader(isr);	
                    String lines;				
					while ((lines = br.readLine()) != null) {				
                        str.append(lines + "\n");
                        Result += "\n" + str.toString()+ "\n";
					}
                    //linesOfFiles.put(file, lines);
					
				} catch (Exception e) {
					return Result = e.getMessage();
				}
               
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

      
       
       return Result;
		
	}
}
