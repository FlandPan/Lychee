
import java.io.Serializable;

public class Wallet implements Serializable {
	
	private static final long serialVersionUID = -5613402205421992568L;
	private double amount;
	
	public Wallet() {
		amount = 0;
	}
	public Wallet(double money){
		amount = money;
	}
	public double getAmount() {
		return amount;
	}
	public boolean fill(double money, long creditCard){
		if (creditCard <= 9999999999999999l && creditCard >= 0){
			amount += money;
			return true;
		}
		else 
			return false;
	}
	public boolean spend(double spent){
		if (amount - spent >= 0){
			amount -= spent;
			return true;
		}
		else
			return false;
	}
}
