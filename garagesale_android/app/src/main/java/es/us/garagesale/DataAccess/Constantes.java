package es.us.garagesale.DataAccess;

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
    private static final String IP = "192.168.1.153"; // Windows: 10.0.2.2, iOS: 192.168.1.4

    /**
     * URLs del Web Service
     */
    public static final String GET_OFFER_INTERESTED = PROTOCOL + IP + "/offer/get_offer_interested.php";
    public static final String GET_ALL_OFFERS = PROTOCOL + IP + "/offer/get_offer.php";
    public static final String GET_OFFER_BY_ID = PROTOCOL + IP + "/offer/get_offer_detail.php";
    public static final String GET_RANKING_BY_ID = PROTOCOL + IP + "/person/get_ranking_by_id.php";
    public static final String GET_ALL_USERNAME_OFFERS = PROTOCOL + IP + "/offer/get_username_offers.php";
    public static final String GET_PERSON_BY_USERNAME = PROTOCOL + IP + "/person/get_person_info.php";
    public static final String UPDATE_OFFER = PROTOCOL + IP + "/offer/update_offer.php";
    public static final String DELETE_OFFER = PROTOCOL + IP + "/offer/delete_offer.php";
    public static final String INSERT_OFFER = PROTOCOL + IP + "/offer/insert_offer.php";
    public static final String GET_LOGIN_VALID = PROTOCOL + IP + "/person/get_login_valid.php";
    public static final String INSERT_OFFER_INTERESTED = PROTOCOL + IP + "/offer/offer_interested.php";
    public static final String INSERT_PERSON = PROTOCOL + IP + "/person/insert_person.php";
    public static final String INSERT_PURCHASE = PROTOCOL + IP + "/offer/insert_purchase.php";
    public static final String INSERT_RANKING = PROTOCOL + IP + "/person/insert_ranking.php";
    public static final String GET_ALL_USERNAME_PURCHASES =  PROTOCOL + IP + "/offer/get_username_purchases.php";

    /**
     * Clave para el valor extra que representa al identificador de una oferta
     */
    public static final String EXTRA_ID = "IDEXTRA";

}