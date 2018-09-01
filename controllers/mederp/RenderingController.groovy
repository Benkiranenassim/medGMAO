package mederp

import org.springframework.dao.DataIntegrityViolationException
import org.krysalis.barcode4j.impl.code39.Code39Bean

import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType

class RenderingController {
	MultiFormatWriter barCodeWriter = new MultiFormatWriter()
	
	def equipementBarcode = {
        // Create and configure the generator
        def generator = new Code39Bean()
        generator.height = 10

        def barcodeValue
		
		barcodeValue = params.equipementcode?:"not found"
		barcodeValue = 'ECD - '+barcodeValue

        renderBarcodePng(generator, barcodeValue)
    }
	
	def equipementBarid = {
        // Create and configure the generator
        def generator = new Code39Bean()
        generator.height = 10

        def barcodeValue
		
		barcodeValue = params.equipementidentifiant?:"not found"
		barcodeValue = 'EID - '+barcodeValue

        renderBarcodePng(generator, barcodeValue)
    }
	
	def userVcard = {
		User user
		if (params.userid) {
			user = User.get(params.userid)
		}
		if (!user) {
			render ('not found')
		}
		String nomprenom = user.nom?:user.username
		nomprenom +=  user.prenom?:" "
		//println nomprenom
		
		String data = """BEGIN:VCARD
VERSION:3.0
FN:${nomprenom}
TEL;TYPE=WORK;CELL:${user.tel.replaceAll("","")}
EMAIL;TYPE=WORK:${user.email}
URL;TYPE=WORK:http://www.fckm.ma/
END:VCARD"""
		
		//String photo = "PHOTO;VALUE=URL;TYPE=GIF:http://www.example.com/dir_photos/my_photo.gif"
		//ADR;TYPE=INTL,POSTAL,WORK:;;Velitrae Ox Head avenue, 1;Rome;Augusta;14567;Maroc
		//TITLE:Dr
		
		int width = 250
		int height= 250
		BarcodeFormat format = BarcodeFormat.QR_CODE
		Hashtable hints = [(EncodeHintType.CHARACTER_SET): 'UTF8']
		BitMatrix bitMatrix = barCodeWriter.encode(data, format, width, height, hints)
		MatrixToImageWriter.writeToStream(bitMatrix, "png", response.outputStream)
	}
	
}
