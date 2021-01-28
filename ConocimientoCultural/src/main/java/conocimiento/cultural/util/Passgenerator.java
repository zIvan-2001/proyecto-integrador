package conocimiento.cultural.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Passgenerator {
	//cambia la contraseña guardada a encriptada manualmene

	public static void main(String ...args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
       
        // System.out.println(bCryptPasswordEncoder.encode("1234"));
       
   // }
}
}