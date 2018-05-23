<?php
/**
 * Get all photos for offer id.
 */

if ($_SERVER['REQUEST_METHOD'] == 'GET')
{
	$succeeded = isset($_GET["offerId"]) && isset($_GET["singlePhoto"]);
	
	if($succeeded)
	{	
		$offerId = $_GET["offerId"];
		$getSinglePhoto = ($_GET["singlePhoto"] == "true");
		$offerPhotosPath = "../photo_storage/" . $offerId . '/';	
		
		$succeeded = file_exists($offerPhotosPath);
		
		$photoDirectory = false;
		if($succeeded) $photoDirectory = opendir($offerPhotosPath);
		$succeeded = ($photoDirectory !== false);
	
		$photosContent = array();
		
		while($succeeded && ($fileName = readdir($photoDirectory)) !== false)
		{
			if($fileName == '.' || $fileName == "..") continue;
			
			$filePath = $offerPhotosPath . $fileName;
			$photoFile = fopen($filePath, "r");
			$succeeded = ($photoFile !== false);
			
			if($photoFile)
			{
				$photoContent = fread($photoFile, filesize($filePath));
				fclose($photoFile);
				array_push($photosContent, $photoContent);
				
				if($getSinglePhoto) break;
			}		
		}
		if($photoDirectory) closedir($photoDirectory);
	}	
	
	if ($succeeded)
	{		
        $response["estado"] = 1;
        $response["photos"] = $photosContent;
		
		print json_encode($response);
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
