package utils;

public enum Constants {
   GERENCIADOR_FILE("gerenciador.txt"),
   GERENCIADOR_ENCRYPTED("gerenciador.encrypted"),
   MASTERKEY_FILE("masterkey.txt");
   
   private final String value;
   
   Constants(String value) {
       this.value = value;
   }
   
   public String getValue() {
       return this.value;
   }
}
