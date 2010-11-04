package jcolibri.test.quiniela;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;
import jcolibri.extensions.classification.ClassificationSolution;

public class Solucion implements ClassificationSolution, CaseComponent 
{
	//String caseId;
	String res;

	@Override
	public Attribute getIdAttribute() 
	{
		return new Attribute ("res",this.getClass() ); //?Solucion.class
	}

	public String getRes() {
		return res;
	}

	public void setRes(String resultado) {
		this.res = resultado;
	}
	
	public String toString()
	{
		return res;
	}

	@Override
	public Object getClassification() {
		return null;
	}

//	public String getCaseId() {
//		return caseId;
//	}
//
//	public void setCaseId(String caseId) {
//		this.caseId = caseId;
//	}
	
	
	
}
