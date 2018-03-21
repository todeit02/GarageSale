package com.example.mariaventura.pruebaframe.DataAccess;

/**
 * Clase que contiene los códigos usados en "I Wish" para
 * mantener la integridad en las interacciones entre actividades
 * y fragmentos
 */
public class Constantes {
    /**
     * Transición Home -> Detalle
     */
    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;

    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica.
     */
    private static final String PUERTO_HOST = "3306";

    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = "http://10.0.2.2:";
    /**
     * URLs del Web Service
     */
    public static final String GET = IP + PUERTO_HOST + "/GarageSale/get_post.php";
    public static final String GET_BY_ID = IP + PUERTO_HOST + "/GarageSale/get_post_detail.php";
    public static final String UPDATE = IP + PUERTO_HOST + "/GarageSale/update_post.php";
    public static final String DELETE = IP + PUERTO_HOST + "/GarageSale/delete_post.php";
    public static final String INSERT = IP + PUERTO_HOST + "/GarageSale/insert_post.php";

    /**
     * Clave para el valor extra que representa al identificador de un post
     */
    public static final String EXTRA_ID = "IDEXTRA";

}