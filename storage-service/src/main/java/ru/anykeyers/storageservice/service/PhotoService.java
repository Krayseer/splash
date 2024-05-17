package ru.anykeyers.storageservice.service;

import java.util.List;

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

    /**
     * Загрузить список фотографий
     *
     * @param photoBytesCollection список фотографий в байтовом формате
     * @return список URL-ов, по которым можно получить фотографию
     */
    List<String> uploadPhotos(List<byte[]> photoBytesCollection);

}
