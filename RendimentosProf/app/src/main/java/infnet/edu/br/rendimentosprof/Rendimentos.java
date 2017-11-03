package infnet.edu.br.rendimentosprof;


public class Rendimentos {

    private double salarioBruto;
    private double pensao;
    private double numDepends;
    private double planoSaude;
    private double inss;
    private double irpf;
    private double outros;
    private double salarioLiquido;
    private double totalDesc;
    private static final Double VALOR_POR_DEPENDENTE = 189.59;

public Rendimentos (double salarioBruto, double pensao, double numDepends, double planoSaude, double outros){

    this.salarioBruto = salarioBruto;
    this.pensao = pensao;
    this.numDepends = numDepends;
    this.planoSaude = planoSaude;
    this.outros = outros;

    this.inss = descontoINSS(this.salarioBruto);
    this.irpf = descontoIRPF(this.salarioBruto);

    this.salarioLiquido = (this.salarioBruto - this.pensao - this.planoSaude - this.inss - this.irpf - this.outros - (this.numDepends * VALOR_POR_DEPENDENTE));
    this.totalDesc = salarioBruto - salarioLiquido;
}

public Double descontoINSS (double salarioBruto){
    double inss;

    if(salarioBruto <= 1659.38){
        inss = salarioBruto * 0.08;
    } else if(salarioBruto <= 2765.66){
        inss = salarioBruto * 0.09;
    } else if(salarioBruto <= 5531.31){
        inss = salarioBruto * 0.11;
    } else {
    inss = 608.44;
    }

    return inss;
}

    public Double descontoIRPF (double salarioBruto){
        double irpf;

        if(salarioBruto <= 1903.98){
            irpf = salarioBruto * 0;
        } else if(salarioBruto <= 2826.65){
            irpf = salarioBruto * 0.075;
        } else if(salarioBruto <= 3751.05){
            irpf = salarioBruto * 0.15;
        } else if(salarioBruto <= 4664.68){
            irpf = salarioBruto * 0.225;
        } else {
            irpf = salarioBruto * 0.275;
        }

        return irpf;
    }

    public Double getSalarioLiq() {

        return salarioLiquido;
    }

    public Double getTotalDesc() {

        return totalDesc;
    }

    public Double getPorcDesc(){

        return (totalDesc/salarioBruto) * 100;
    }

}
