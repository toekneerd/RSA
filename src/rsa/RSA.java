package rsa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Anthony DeCarlo & Daniel Elice
// 05/11/2017
// MATH314.
public class RSA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = getN(); //Input value for n
        int e = getE(); //Input value for e
        List<Integer> list = factor(n); //Store the two prime factors into a list
        int p = list.get(0); // First prime factor
        int q = list.get(1); //Second prime factor
        int d = calcD(e,p,q); // Calculates d value
        System.out.println("Enter your message (c), enter a non-integer/character to quit"); 
        while(in.hasNext())     //User inputs their ciphertext while there is input
        {
        if(!in.hasNextInt())
           break;
        else{
        int c = in.nextInt(); 
        int m = modExp(c,d,n);  //Modular exponentiation
        int[] word = decrypt(m); //Decrypts the message and stores the block into an array
        String alphabet = "abcdefghijklmnopqrstuvwxyz"; 
        for(int i = 0; i <word.length;i++)
            System.out.print(alphabet.charAt(word[i])); //Translates the numbers into letters

        }
        }
        
        
        
        
        
    }
    
    
    
    public static int getN(){
           System.out.println("Please enter your n value (0-100,000)"); 
             int n = getInt();
            while ((n<0) || (n>100000)){ //User must enter a valid integer between 0 and 100,000
                System.out.println("Enter a valid n value (0-100,000)");
                n = getInt();
            }
           
            return n;
       }
    
        public static int getE(){
           System.out.println("Please enter your e value (0-100,000)");
            int e = getInt();
            while ((e<0) || (e>100000)){ //User must enter a valid integer between 0 and 100,000
                System.out.println("Enter a valid e value (0-100,000)");
                e = getInt();
            }
           
            return e;
       }
        
    public static List<Integer> factor(int x){ //Prime factorization of x values
        List<Integer> factor = new ArrayList<Integer>();
        int count;
        for (int i = 2; i<=(x); i++) { 
            count=0;
            while (x % i == 0) { 
                x /= i;
                count++;
            }
        if (count == 0) continue;
            factor.add(i);

    }
        return factor;
    }
    
    public static int modInv(int x,  int y) //Inverse mod using Extended Euclidian algorithm
    {
    int inv, x1, x3, y1, y3, z1, z3, q;
    int iter;
    x1 = 1;
    x3 = x;
    y1 = 0;
    y3 = y;
   
    iter = 1;
    //Loop while y3 != 0 
    while (y3 != 0)
    {
        /* Step X3. Divide and "Subtract" */
        q = x3 / y3;
        z3 = x3 % y3;
        z1 = x1 + q * y1;
        /* Swap */
        x1 = y1; y1 = z1; x3 = y3; y3 = z3;
        iter = -iter;
    }
    //Make sure x3 = gcd(x,y) == 1 
    if (x3 != 1)
        return 0;   // No inverse exists 
    // Positive result
    if (iter < 0)
        inv = y - x1;
    else
        inv = x1;
    return inv;
    }
    
    public static int[] decrypt(int m){ //Inverts the process of converting blocks of three letters into a number
        int[] word = new int[3];  //Create an array to store each letter
        int a; int b; int c;
        a = m % 26;                 //Calculates first letter
        b = ((m - a) / 26) % 26;    //Calculates second letter
        c = (((m - a) / 26) - b) / 26; //Calculates last letter
        word[0] = c; 
        word[1] = b;
        word[2] = a; //Stores the letters into an array
        return word;     
    }
    
    public static int calcD(int e, int p, int q){ //Formula for calculating d
        int d = modInv(e,(p-1)*(q-1)); //Calls modInv method to calculate e^-1(mod(p-1)(q-1))
        return d;
    }
    
    public static int modExp(int x, int y, int p){ //Modular exponentiation using repeated squaring method
        int result = 1; //Initialize
        x = x % p;
        while(y>0){
            if (y % 2 == 1) //y value is odd
                result = (result*x) % p;
            // y value is even
            y = y >> 1;
            x = (x*x) %p;
        }
        return result;
    }
    
  
        
    public static int getInt() //Looping input validation that only accepts integers from keyboard
    {
     Scanner inp = new Scanner(System.in);
     int intInp = 0;
     boolean valid;
     do
     {
         if(inp.hasNextInt())
         {
         intInp = inp.nextInt();
         valid = true;
         }
         else
         {
         valid = false;
         inp.next();
         System.out.println("Not a valid integer! Please try again!");     
         }
     }
        while (!valid);
        return intInp;
     }
}
