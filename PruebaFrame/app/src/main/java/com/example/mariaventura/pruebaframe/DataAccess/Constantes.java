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
    private static final String PORT_HOST = "3306";

    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String PROTOCOL = "http://";
    private static final String IP = "10.0.2.2";
    /**
     * URLs del Web Service
     */
    public static final String GET = PROTOCOL + IP + "/Post/get_post.php";
    public static final String GET_BY_ID = PROTOCOL + IP + "/Post/get_post_detail.php";
    public static final String UPDATE = PROTOCOL + IP + "/Post/update_post.php";
    public static final String DELETE = PROTOCOL + IP + "/Post/delete_post.php";
    public static final String INSERT = PROTOCOL + IP + "/Post/insert_post.php";

    /**
     * Clave para el valor extra que representa al identificador de un post
     */
    public static final String EXTRA_ID = "IDEXTRA";

}