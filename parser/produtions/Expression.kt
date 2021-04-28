package parser.produtions

import utils.ClassType

class Expression {
    /*
    fun expression() {
        orExpression()
    }

    private fun orExpression() {
        andExpression()
        var token = lexer.nextToken()
        if(token != null && token.value == "||") {
            consumedTokens.add(token)
            orExpression()
        } else if(token != null) {
            lexer.returnToken(token)
        }
    }

    private fun andExpression() {
        equalityExpression()
        var token = lexer.nextToken()
        if(token != null && token.value == "&&") {
            consumedTokens.add(token)
            andExpression()
        } else if(token != null) {
            lexer.returnToken(token)
        }
    }

    private fun equalityExpression() {
        compareExpression()
        var token = lexer.nextToken()
        if(token != null && token.value == "==") {
            consumedTokens.add(token)
            equalityExpression()
        } else if(token != null && token.value == "!=") {
            consumedTokens.add(token)
            equalityExpression()
        } else if(token != null) {
            lexer.returnToken(token)
        }
    }

    private fun compareExpression() {
        addExpression()
        var token = lexer.nextToken()
        if(token != null && token.value == "<") {
            consumedTokens.add(token)
            compareExpression()
        } else if(token != null && token.value == ">") {
            consumedTokens.add(token)
            compareExpression()
        } else if (token != null && token.value == "<=") {
            consumedTokens.add(token)
            compareExpression()
        } else if (token != null && token.value == ">=") {
            consumedTokens.add(token)
            compareExpression()
        } else if (token != null) {
            lexer.returnToken(token)
        }
    }

    private fun addExpression() {
        multiplicationExpression()
        var token = lexer.nextToken()
        if(token != null && token.value == "+") {
            consumedTokens.add(token)
            addExpression()
        } else if(token != null && token.value == "-") {
            consumedTokens.add(token)
            addExpression()
        } else if(token != null) {
            lexer.returnToken(token)
        }
    }

    private fun multiplicationExpression() {
        unaryExpression()
        var token = lexer.nextToken()
        if(token != null && token.value == "*") {
            consumedTokens.add(token)
            multiplicationExpression()
        } else if(token != null && token.value == "/") {
            consumedTokens.add(token)
            multiplicationExpression()
        } else if(token != null) {
            lexer.returnToken(token)
        }
    }

    private fun unaryExpression() {
        var token = lexer.nextToken()
        if(token != null && token.value == "!") {
            consumedTokens.add(token)
            unaryExpression()
        } else if(token != null) {
            lexer.returnToken(token)
            primaryExpression()
        }
    }

    private fun primaryExpression() {
        var token = lexer.nextToken()
        if(token != null && token.value == "(") {
            consumedTokens.add(token)
            expression()
            token = lexer.nextToken()
            if(token != null && token.value == ")") {
                consumedTokens.add(token)
                return
            }
        } else if(token != null && (token.type.type == ClassType.ID || token.value == "true" || token.value == "false" ||
                    token.type.type == ClassType.NUMBER || token.type.type == ClassType.STRING)) {
            consumedTokens.add(token)
            return
        }
    }

     */
}