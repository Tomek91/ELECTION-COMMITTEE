package pl.com.app.config;

public interface MultipartSettings {
    String LOCATION = "C:/mytemp";      // gdzie beda przechowywane 'kawalki' przed scaleniem
    long MAX_FILE_SIZE = 5242880;       // jaki jest max rozmiar jednego przesylanego pliku
    long MAX_REQUEST_SIZE = 20971520;   // jaki jest max rozmiar wszystkich przesylanych plikow w jednym zadaniu
    int FILE_SIZE_THRESHOLD = 0;        // powyzej jakiego rozmiaru plik bedzie przesylany z wykorzystaniem multipart
}
