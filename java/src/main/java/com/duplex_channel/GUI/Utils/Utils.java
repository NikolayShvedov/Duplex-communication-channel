package com.duplex_channel.GUI.Utils;

public class Utils {

    //настройки окна
    public static int WIDTH = 550;
    public static int HEIGHT = 400;

    //размер средних объектов q-схемы
    public static int SIZE = 40;

    //размер маленьких объектов q-схемы
    public static int BACKLASH = 5;

    //расстояние между объектами q-схемы
    public static int DIFFERENCE = 30;

    //размер пакетов
    public static int TRANSACT_SIZE = 10;

    //один шаг пакета
    public static int STEP = 5;

    //задержка
    public static int DELAY = 10;

    //количество моделирований
    public static int RUN_COUNT = 3;

    //среднее время генерации
    public static int GENERATE_TIME = 10;

    //среднее время отклонения при генерации пакета
    public static int DEVIATION_GENERATE_TIME = 3;

    //среднее время отклонения в спутниковой полудуплексной линии связи
    public static int DEVIATION_SPUTNIK_LINE = 5;

    //время моделирования
    public static int TIME = 60 * 100;

    //количество транзакотов в буфере
    public static int QUEUE_PACKAGE_COUNT = 2;
}
