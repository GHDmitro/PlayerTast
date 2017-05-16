package exceptions;

/**
 * Created by Dmytro Tymoshenko on 15.05.17.
 * IllegalNameException : class which throws if players names or numbers are same, this situation
 *                        is forbidden
 */
public class IllegalNameException extends Exception {


    /**
     * str: exception`s description
     */
    private String str;
    public IllegalNameException(String str){
        this.str = str;
    }

    public String getStr(){
        return str;
    }

}
