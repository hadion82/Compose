package com.example.lint

import com.android.tools.lint.detector.api.AnnotationInfo
import com.android.tools.lint.detector.api.AnnotationUsageInfo
import com.android.tools.lint.detector.api.AnnotationUsageType
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.ClassScanner
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.UastLintUtils.Companion.tryResolveUDeclaration
import org.jetbrains.kotlin.psi.KtIfExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.getContainingUMethod
import org.jetbrains.uast.getUCallExpression

open class MapLifecycleDetector : Detector(), SourceCodeScanner, ClassScanner {

    companion object {
        const val MAP_READY_ANNOTATION = "{package}.MapReady"
        const val MAP_LIFE_CYCLE_ANNOTATION = "{package}.MapLifeCycle"
        const val MAP_LIFE_CYCLE_METHOD_NAME = "isReady"
        const val FRAGMENT_LIFE_CYCLE_METHOD_NAME = "isAdded"
        const val ACTIVITY_LIFE_CYCLE_METHOD_NAME = "isFinishing"
        private const val MEMBER_CLASS_MAP_EXTENSIONS = "{package}.MapExtensions"

        val ISSUE = Issue.create(
            id = "MapReady",
            briefDescription = "Check map life cycle",
            explanation = "Use Fragment.isAdded , Activity.isFinishing, @MapLifecycle method",
            category = Category.CORRECTNESS,
            priority = 5,
            severity = Severity.ERROR,
            implementation = Implementation(MapLifecycleDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun visitAnnotationUsage(
        context: JavaContext,
        element: UElement,
        annotationInfo: AnnotationInfo,
        usageInfo: AnnotationUsageInfo
    ) {
        if (usageInfo.type != AnnotationUsageType.METHOD_CALL) return
        val containMethod = element.getContainingUMethod()
        if(isMapReady(context, element.uastParent, containMethod)) return

        context.report(
            ISSUE,
            element,
            context.getLocation(element),
            "Must check life cycle before using the map, use isReady in MapExtensions"
        )
    }

    private fun isMapReady(context: JavaContext, node: UElement?, containMethod: UMethod?): Boolean {
        if (containMethod?.findAnnotation(MAP_READY_ANNOTATION) != null) return true
        var isActive = false
        var isReady = false
        var parent = node
        do {
            val sourcePsi = parent?.sourcePsi
            if (sourcePsi is KtIfExpression && sourcePsi.condition?.children?.any { psi ->
                    psi.children.any { c ->
                        val string = c.text
                        isActive = isActive || isActiveOwner(string)
                        isReady = isReady || isReady(string)
                        isActive && isReady
                    }
                } == true) return true

            val resolvedDec = parent?.tryResolveUDeclaration()
            if(resolvedDec?.findAnnotation(MAP_LIFE_CYCLE_ANNOTATION) != null) return true

            val call = parent?.getUCallExpression()
            if(call?.methodName == MAP_LIFE_CYCLE_METHOD_NAME &&
                context.evaluator.isMemberInClass(call.resolve(), MEMBER_CLASS_MAP_EXTENSIONS)
            ) return true

            parent = parent?.uastParent
        } while (parent != null)

        return false
    }

    private fun isActiveOwner(s: String) =
        s == FRAGMENT_LIFE_CYCLE_METHOD_NAME || s == ACTIVITY_LIFE_CYCLE_METHOD_NAME

    private fun isReady(s: String) =
        s == MAP_LIFE_CYCLE_METHOD_NAME
}