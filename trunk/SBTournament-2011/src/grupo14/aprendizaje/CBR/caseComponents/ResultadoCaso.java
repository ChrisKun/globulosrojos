package grupo14.aprendizaje.CBR.caseComponents;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

/**
 * Clase que especifica si las acciones que se tomaron en un caso concreto fueron
 * las acertadas o por el contrario el caso fue contraproducente
 * @author markel
 *
 */
public class ResultadoCaso implements CaseComponent{
        
        /**
         * Valoración del caso en base a si las acciones tomadas fueron las correctas.
         * El valor mínimo del caso es 0 y el máximo 1. Por ejemplo, un caso en el que el equipo se
         * encontraba defendiendo y terminó marcando gol, debería estar muy cerca del 1.
         */
        private Float valoracionCaso;
        
        public ResultadoCaso(){
        }
        
        public ResultadoCaso(float valoracionCaso){
        	this.valoracionCaso = valoracionCaso;
        }
        
        @Override
        public Attribute getIdAttribute() {
                return new  Attribute("valoracionCaso", this.getClass());
        }

        public float getValoracionCaso() {
                return valoracionCaso;
        }

        public void setValoracionCaso(float valoracionCaso) {
                this.valoracionCaso = valoracionCaso;
        }

}
