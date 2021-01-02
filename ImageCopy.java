package util;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ImageCopy {


	public static String Copiafile(String orig,String dest){
	try{
	FileInputStream in = new FileInputStream(orig);
	FileOutputStream out = new FileOutputStream(dest);
	while(true) {
	int data = in.read();
	if(data == -1)
	break;
	else
	out.write(data);
	}
	in.close();
	out.close();
	}
	catch (Exception e){
	e.printStackTrace();
	}
	return dest;

	}
	
}
