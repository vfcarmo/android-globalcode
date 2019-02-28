package com.example.exercicio2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.*
import com.example.exercicio1.Genero
import com.example.exercicio1.Usuario
import java.util.regex.Pattern

class EditActivity : AppCompatActivity() {

    companion object {
        const val USUARIO = "USUARIO"
    }

    private lateinit var etNome: EditText
    private lateinit var etSobrenome: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etPasswordConfirm: EditText
    private lateinit var rgGenero: RadioGroup
    private lateinit var rbMasculino: RadioButton
    private lateinit var rbFeminino: RadioButton

    private var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        this.etNome = findViewById(R.id.etNome)
        this.etSobrenome = findViewById(R.id.etSobrenome)
        this.etEmail = findViewById(R.id.etEmail)
        this.etPassword = findViewById(R.id.etPassword)
        this.etPasswordConfirm = findViewById(R.id.etPasswordConfirm)
        this.rgGenero = findViewById(R.id.rgGenero)
        this.rbMasculino = findViewById(R.id.rbMasculino)
        this.rbFeminino = findViewById(R.id.rbFeminino)

        val btSalvar: Button = findViewById(R.id.btSalvar)
        btSalvar.setOnClickListener {
            salvar()
        }
        val btCancelar: Button = findViewById(R.id.btCancelar)
        btCancelar.setOnClickListener {
            cancelar()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (usuario != null) {
            outState?.putParcelable(USUARIO, usuario)
        } else {
            outState?.remove(USUARIO)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        val usuario:Usuario? = savedInstanceState?.getParcelable(USUARIO) as Usuario
        if (usuario != null) {
            this.usuario = usuario
        }
    }

    private fun salvar() {
        val nome = etNome.text.toString()
        val sobrenome = etSobrenome.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val passwordConfirm = etPasswordConfirm.text.toString()
        val idGeneroSelecionado = rgGenero.checkedRadioButtonId

        val valid = isValid(nome, sobrenome, email, password, passwordConfirm, idGeneroSelecionado)

        if (valid) {

            val genero = if (idGeneroSelecionado == R.id.rbMasculino) {
                Genero.MASCULINO
            } else {
                Genero.FEMININO
            }

            val usuario = Usuario(nome, sobrenome, email, password, genero)

            val intent: Intent = intent
            intent.putExtra("RESULT", usuario)
            setResult(Activity.RESULT_OK, intent)

            Log.d(
                localClassName, "Usuário { nome=${usuario.nome}, sobrenome=${usuario.sobrenome}, " +
                        "email=${usuario.email}, genero=${usuario.genero.name.toLowerCase()}, password=*** }"
            )
            finish()
        }
    }

    private fun cancelar() {
        finish()
    }

    private fun isValid(
        nome: String, sobrenome: String, email: String, password: String,
        passwordConfirm: String, idGeneroSelecionado: Int
    ): Boolean {
        var result = true
        var requestFocus = false
        if (nome.isEmpty()) {
            etNome.setError("Nome é obrigatório!")
            result = false
            if (!requestFocus) {
                etNome.requestFocus()
                requestFocus = true
            }
        } else {
            etNome.error = null
        }

        if (sobrenome.isEmpty()) {
            etSobrenome.setError("Sobrenome é obrigatório!")
            result = false
            if (!requestFocus) {
                etSobrenome.requestFocus()
                requestFocus = true
            }
        } else {
            etSobrenome.error = null
        }

        if (email.isEmpty()) {
            etEmail.setError("E-mail é obrigatório!")
            result = false
            if (!requestFocus) {
                etEmail.requestFocus()
                requestFocus = true
            }
        } else {
            val emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
            if (!emailPattern.matcher(email).find()) {
                etEmail.setError("E-mail inválido!")
                result = false
            } else {
                etEmail.error = null
            }
        }

        if (password.isEmpty()) {
            etPassword.setError("Senha é obrigatória!")
            result = false
            if (!requestFocus) {
                etPassword.requestFocus()
                requestFocus = true
            }
        } else {
            etPassword.error = null
        }

        if (passwordConfirm.isEmpty()) {
            etPasswordConfirm.setError("Confirmação da Senha é obrigatória!")
            result = false
            if (!requestFocus) {
                etPasswordConfirm.requestFocus()
                requestFocus = true
            }
        } else {
            etPasswordConfirm.error = null
        }

        if (password.isNotEmpty() && passwordConfirm.isNotEmpty()) {
            if (password != passwordConfirm) {
                etPassword.setError("Senha e Confirmação da Senha precisam ser iguais!")
                etPasswordConfirm.setError("Senha e Confirmação da Senha precisam ser iguais!")
                result = false
                if (!requestFocus) {
                    etPassword.requestFocus()
                }
            } else {
                etPassword.error = null
                etPasswordConfirm.error = null
            }
        }

        if (idGeneroSelecionado < 0) {
            rbFeminino.setError("Selecione um gênero!")
            result = false
        }
        return result
    }
}
