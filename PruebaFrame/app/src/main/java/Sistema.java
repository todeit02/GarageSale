/**
 * Created by mariaventura on 13/3/18.
 */

public class Sistema {
    private static Sistema sistema;
    public  Sistema GetSistema(){
        if (sistema== null){
            sistema = new Sistema();
        }
        return sistema;
    }
    private Sistema(){

    }


}
