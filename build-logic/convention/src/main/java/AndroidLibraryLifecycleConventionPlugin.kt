import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryLifecycleConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            dependencies {
                add("implementation", libs.findLibrary("lifecycle-runtime-ktx").get())
                add("implementation", libs.findLibrary("lifecycle-runtime-compose").get())
                add("implementation", libs.findLibrary("lifecycle-viewmodel-compose").get())
            }
        }
    }
}