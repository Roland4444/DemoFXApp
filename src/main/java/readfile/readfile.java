package readfile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class readfile {
    public String read(){
        String url_db="";
        FileInputStream fprop;
        Properties property = new Properties();
        try {
            // Файл свойств  находится в корневой папке проекта
            fprop = new FileInputStream("conf.prop");
            property.load(fprop);
            //  Чтение строки соединения из файла свойств
            url_db = property.getProperty("URL_DB");
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
        return url_db;
    }
}
