package co.alvarorubiano.App2067710_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

enum class ModoCalculadora{
    Nada,Sumar,Restar,Multiplicar,Dividir
}

class MainActivity : AppCompatActivity() {

    var lastButtonWasMode = false
    var currentMode = ModoCalculadora.Nada
    var labelString = ""
    var savedNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCalculator()
    }

    private fun setupCalculator(){
        //Se declara un arreglo para todos los botones numericos
        val allButtons = arrayOf(cero, uno, dos, tres, cuatro, cinco, seis, siete, ocho, nueve)

        //definir setOnClickListener para cada uno de los botones
        for (i in allButtons.indices){
            allButtons[i].setOnClickListener { didPressNumber(i) }
        }

        //definir setOnClickListener para los botones de operaciones, igual y borrado
        suma.setOnClickListener { changeMode(ModoCalculadora.Sumar) }
        resta.setOnClickListener { changeMode(ModoCalculadora.Restar) }
        multiplicar.setOnClickListener { changeMode(ModoCalculadora.Multiplicar) }
        dividir.setOnClickListener { changeMode(ModoCalculadora.Dividir) }
        borrado.setOnClickListener { didPressClear() }
        igual.setOnClickListener { didPressEqual() }

    }//Termina el setupCalcula

    fun didPressNumber(num:Int){
        val strVal = num.toString()

        if(lastButtonWasMode){
            lastButtonWasMode = false
            labelString = "0"
        }

        labelString = "$labelString$strVal"
        updateText()
    }

    private fun updateText() {
        //todo -> controlar overflow de números
        if(labelString.length > 8 ){
            didPressClear()
            pantalla.text = "Too big"
        }

        val labelInt = labelString.toInt()
        labelString = labelInt.toString()
        if(currentMode == ModoCalculadora.Nada){
            savedNum = labelInt
        }
        // Dar formato de miles
        //val formato = DecimalFormat(pattern: "#,###")

        pantalla.text = labelString
    }

    fun changeMode(mode: ModoCalculadora){
        //para averiguar cual es el último guardado. Si el numero es O no debe hacer nada
        if(savedNum == 0){
            return
        }
        currentMode = mode
        lastButtonWasMode = true
    }

    //Método para borrar la pantalla
    fun didPressClear(){
        lastButtonWasMode = false
        currentMode = ModoCalculadora.Nada
        labelString = ""
        savedNum = 0
        pantalla.text = "0"
    }

    fun didPressEqual(){
        //Verificar si el usuario invocó alguna operación
        if(lastButtonWasMode){
            return
        }

        //Convertir la entrada del usuario en entero
        val labelInt = labelString.toInt()

        //Dependiendo del modo invocado por el usuario a ejecutar
        when(currentMode){
            ModoCalculadora.Sumar -> savedNum += labelInt
            ModoCalculadora.Restar -> savedNum -= labelInt
            ModoCalculadora.Multiplicar -> savedNum *= labelInt
            ModoCalculadora.Dividir -> if(labelInt > 0){savedNum /= labelInt}else{didPressClear()}
            ModoCalculadora.Nada -> return
        }

        currentMode = ModoCalculadora.Nada
        labelString = "$savedNum"
        updateText()
        lastButtonWasMode = true
    }


}
