package com.example.lint

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

class MethodCallDetector : MapLifecycleDetector() {

    companion object {
        val ISSUE = Issue.create(
            id = "AlarmManager",
            briefDescription = "Parameter count check",
            explanation = "check setRepeating method parameters",
            category = Category.CORRECTNESS,
            priority = 5,
            severity = Severity.ERROR,
            implementation = Implementation(MapLifecycleDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableMethodNames(): List<String> {
        return listOf(
            "setRepeating"
        )
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val evaluator = context.evaluator
        if (evaluator.isMemberInClass(method, "android.app.AlarmManager") &&
            evaluator.getParameterCount(method) == 4
        ) {
            context.report(
                ISSUE,
                node,
                context.getLocation(node),
                "Invalid parameter count"
            )
        }
    }
}