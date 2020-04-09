import java.util.ArrayList;

public class Variable {
    public char value;
    public ArrayList<Character> domain;

    public Variable(char value){
        this.value = value;
        this.domain = new ArrayList<Character>();
    }

    public Variable(Variable variable){
        this.value = variable.value;
        this.domain = variable.domain;
    }

    public int getDomainSize(){
        return domain.size();
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }

    public String write(){
        return "Variable{" +
                "value=" + value +
                '}';
    }

    public void writeDomain(){
        if(domain.size() > 0) {
            for (Character ch : domain) {
                System.out.print(ch + ", ");
            }
            System.out.println();
        }
    }
}
