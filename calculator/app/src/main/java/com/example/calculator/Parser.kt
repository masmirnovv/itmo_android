package com.example.calculator

class Parser(expr: StringBuilder) {


    private var pos: Int = 0
    private var expression: StringBuilder = StringBuilder(expr)

    private val ENDCHAR: Char = Char.MIN_VALUE

    fun parse(): Double {
        expression.append(ENDCHAR)
        val res = nextSum()
        if (getCh() != ENDCHAR)
            throw CalcException("Ошибка парсинга")
        return res
    }


    private fun nextSum(): Double {
        var res = nextMult()
        while (getCh() == '+' || getCh() == '-') {
            if (getCh() == '+') {
                nextCh()
                res += nextMult()
            } else {
                nextCh()
                res -= nextMult()
            }
        }
        return res
    }

    private fun nextMult(): Double {
        var res = nextNeg()
        while (getCh() == '*' || getCh() == '/') {
            if (getCh() == '*') {
                nextCh()
                res *= nextNeg()
            } else {
                nextCh()
                val div = nextNeg()
                if (div == 0.0)
                    throw CalcException("Деление на 0")
                res /= div
            }
        }
        return res
    }

    private fun nextNeg(): Double {
        if (getCh() == '-') {
            nextCh()
            return -nextAtom()
        }
        return nextAtom()
    }

    private fun nextAtom(): Double {
        if (getCh() == '(') {
            nextCh()
            val res = nextSum()
            if (getCh() != ')')
                throw CalcException("Ошибка парсинга")
            nextCh()
            return res
        } else {
            val num: StringBuilder = StringBuilder()
            while (getCh().isDigit()) {
                num.append(getCh())
                nextCh()
            }
            if (getCh() == '.') {
                num.append('.')
                nextCh()
                while (getCh().isDigit()) {
                    num.append(getCh())
                    nextCh()
                }
            }
            if (num.isEmpty() || (num.toString() == "."))
                throw CalcException("Ошибка парсинга")
            return num.toString().toDouble()
        }
    }



    private fun getCh(): Char {
        return expression[pos]
    }

    private fun nextCh() {
        pos++
    }

}