<?php
/**
 * Obtiene todas las metas de la base de datos
 */

require '../Database.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET')
{

	$username = $_GET['username'];
	$password = $_GET['password'];
	$validCount = null;

    $consulta = "SELECT COUNT(*) FROM persons WHERE username = " . $username . " AND password = " . $password;
	try {
		// Preparar sentencia
		$comando = Database::getInstance()->getDb()->prepare($consulta);
		// Ejecutar sentencia preparada
		$comando->execute();

		$validCount = $comando->fetchAll(PDO::FETCH_ASSOC);
	}
	catch (PDOException $e) {
		$validCount = null;
	}

    if ($validCount)
	{
        $datos["estado"] = 1;
        $datos["isLoginValid"] = boolval( $validCount[0]["COUNT(*)"] );

        print json_encode($datos);
    }
	else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error"
        ));
    }
}

?>