<?php
/**
 * Get all photos for offer id.
 */

if ($_SERVER['REQUEST_METHOD'] == 'GET')
{
	$succeeded = isset($_GET["offerId"]);
	
	if($succeeded)
	{	
		$offerId = $_GET["offerId"];
		$offerPhotosPath = "../photo_storage/" . $offerId . '/';	
		
		$photoDirectory = opendir($offerPhotosPath);
		$succeeded = ($photoDirectory !== false);
	
		$photosContent = array();
		
		foreach(readdir($photoDirectory) as $fileName)
		{
			if($fileName == '.' || $fileName == ".." || !$succeeded || $fileName === false) continue;
			
			$filePath = $offerPhotosPath . $fileName;
			$photoFile = fopen($filePath, "r");
			$succeeded = ($photoFile !== false);
			
			if($photoFile)
			{
				$photoContent = fread($photoFile, filesize($filePath));
				fclose($photoFile);
				array_push($photosContent, $photoContent);
			}		
		}
	}	
	
	if ($succeeded)
	{
        $response["estado"] = 1;
        $response["photos"] = $photosContent;
		
		print json_encode($response);
        );
    }
	else {
		$mensaje = isset($offerId) ? ("No photos available for offerId " . $offerId . '.') : "No offerId has been passed.";
        print json_encode(
            array(
                "estado" => '2',
                "mensaje" => $mensaje)
        );
    }
	
	
	
	
	
	
	$succeeded = false;
	
    $body = json_decode(file_get_contents("php://input"), true);
	$offerId = $body['offerId'];
	$image = $body['image'];
	
	$offerPhotosPath = "../photo_storage/" . $offerId . '/';
	
	if(!file_exists($offerPhotosPath))
	{
		mkdir($offerPhotosPath, 0755, true);
	}
	
	$existentPhotos = glob($offerPhotosPath . "*");
	if ($existentPhotos) $photoName = count($existentPhotos);
	else $photoName = 0;
	
	$photoNameWithExtension = $photoName . ".jpg";
	$photoPath = $offerPhotosPath . $photoNameWithExtension;
	
	$photoFile = fopen($photoPath, "w");
	if($photoFile)
	{
		fwrite($photoFile, $body['image']);
		fclose($photoFile);
		$succeeded = true;
	}
	
	
}
