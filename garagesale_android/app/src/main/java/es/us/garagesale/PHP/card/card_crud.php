<?php

require_once '../Database.php';
 
class CardCrud
{
    function __construct()
    {
    }

    /**
     * Obtiene los campos de un offer con un identificador
     * determinado
     *
     * @param $id Identificador del offer
     * @return mixed
     */
    public static function getById($id)
    {
        // Consulta de la meta
        $consulta = "SELECT * FROM cards WHERE id = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($id));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;

        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return -1;
        }
    }
	
	public static function getByCardNum($cardNum)
	{
		$consulta = "SELECT * FROM cards WHERE cardNum = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($cardNum));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;

        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return -1;
        }
	}
	
	public static function insert(
        $cardNum,
        $expDate,
        $ccv
    )
    {
        // Sentencia INSERT
        $command = "INSERT INTO cards ( " .
            "cardNum, " .
            "expDate, " .
            "ccv )" .
            " VALUES( ?,?,? )";

        // Preparar la sentencia
        $request = Database::getInstance()->getDb()->prepare($command);

        return $request->execute(
            array(
                $cardNum,
				$expDate,
				$ccv
            )
        );
    }
}

?>