package br.com.via1.pad.models;


public enum NomeArquivo { 
	CONVENCAO_COLETIVA_DE_TRABALHO_VIGENTE("1. Convenção Coletiva de Trabalho vigente"),
	CERTIDAO_NEGATIVA_DE_DEBITO_FEDERAL("2. Certidão Negativa de Débito Federal"),
	CERTIDAO_NEGATIVA_DE_DEBITO_JUNTO_A_PREVIDENCIA("3. Certidão Negativa de Débito junto à Previdência"),
	CERTIDAO_NEGATIVA_DE_DEBITO_DE_NATUREZA_ESTADUAL("4. Certidão Negativa de Débito de natureza Estadual"),
	CERTIDAO_NEGATIVA_DE_DEBITO_DE_NATUREZA_MUNICIPAL("5. Certidão Negativa de Débito de natureza Municipal"),
	CERTIDAO_NEGATIVA_DE_DEBITO_REGULARIDADE_DE_FGTS("6. Certidão Negativa de Débito Regularidade de FGTS"),
	LISTA_DOS_FUNCIONARIOS_PRESTADORES_DE_SERVICO_DO_MES("7. Lista dos funcionários prestadores de serviço do Mês"),
	FOLHA_DE_PAGAMENTO("8. Folha de pagamento, com o centro de custo específico para funcionários que prestam serviço na Via Sul, com resumo da folha, identificando os impostos gerados, preferencialmente em ordem alfabética ou numérica"),
	RE_FGTS("9. R.E./FGTS (Relação de Empregados com resumo final) constando a RE específica para funcionários que prestam serviço no Grupo Viasul"),
	CAGED("10. CAGED (Cadastro geral de empregados e desempregados) Recibo de entrega e relação de funcionários envolvidos no caso de presatcação para o Grupo Viasul"),
	INSS_E_COMPROVANTES("11. INSS (Guia Recolhimento do INSS) e comprovantes"),
	GRF_FGTS("12. GRF/FGTS (Guia Recolhimento do FGTS)"),
	RECIBOS_DE_PAGAMENTO_DE_SALARIOS_ASSINADOS_E_DATADOS("13. Recibos de pagamento de salários assinados e datados ou comprovante de depósito bancário emitido pelo próprio Banco, separados conforme a ordem da folha de pagamento dos funcionários que prestam serviços na Via sul OU comprovante de pagamento no Banco"),
	AVISO_E_RECIBO_DE_FERIAS("14. Aviso e Recibo de férias referente à competência entregue, devidamente assinados"),
	TRTC("15. TRCT (Termo de Rescisão de Contrato de Trabalho)"),
	EXAME_MEDICO_DEMISSIONAL("16. Exame Médico Demissional. Em casos de Demissões durante o período de experiência será necessário apresentar o Exame Admissional"),
	GRRF("17. GRRF (Guia de Recolhimento Rescisório do FGTS) + Demonstrativo com os dados do funcionário");
	
	private final String displayValue;
	
	private NomeArquivo(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
}
