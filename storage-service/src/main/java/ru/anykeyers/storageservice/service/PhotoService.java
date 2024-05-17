package ru.anykeyers.storageservice.service;

/**
 * Сервис обработки фотографий
 */
public interface PhotoService {

    /**
     * Загрузить фотографию
     *
     * @param photoBytes фотография в байтовом формате
     * @return URL, по которому можно получить фотографию
     */
    String uploadPhoto(byte[] photoBytes);

}
