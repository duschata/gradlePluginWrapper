/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package de.gipmbh.gradle.plugins.release

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import pl.allegro.tech.build.axion.release.domain.RepositoryConfig
import pl.allegro.tech.build.axion.release.domain.TagNameSerializationConfig
import pl.allegro.tech.build.axion.release.domain.VersionConfig

class GipReleasePlugin : Plugin<Project> {
    override fun apply(project: Project) {

        project.apply(mapOf("plugin" to "pl.allegro.tech.build.axion-release"))

        val versionConfigExtension: VersionConfigExtension =
            project.extensions.create("releaseBranch", VersionConfigExtension::class.java)

        project.extensions.configure<VersionConfig>("scmVersion") { versionConfig: VersionConfig ->
            versionConfig.repository { repositoryConfig: RepositoryConfig ->
                repositoryConfig.directory.set(project.rootProject.file("../"))
            }
            versionConfig.tag { tagNameSerializationConfig: TagNameSerializationConfig ->
                tagNameSerializationConfig.prefix.set(versionConfigExtension.tagPrefix.get())
                tagNameSerializationConfig.initialVersion { _, _ -> "1.0.0" }
            }
            versionConfig.ignoreUncommittedChanges.set(false) // TODO configurable
            versionConfig.useHighestVersion.set(false) //TODO: configurable

            versionConfig.versionCreator("versionWithBranch")
            if (project.hasProperty("ignoreUncommitted")) {
                versionConfig.checks { versionConfig.checks.uncommittedChanges.set(true) }
            }

            versionConfigExtension.version = versionConfig.version

//            if (incrementer.isPresent) {
//                versionIncrementer(leastVersionIncrementer(incrementer.get(), leastVersion.get()))
//            } else {
//                versionIncrementer(leastVersionIncrementer("incrementMinor", leastVersion.get()))
//                branchVersionIncrementer(
//                    mapOf(
//                        "(release/|support/).*" to leastVersionIncrementer(
//                            "incrementPatch",
//                            leastVersion.get()
//                        )
//                    )
//                )
//            }
//            fun leastVersionIncrementer(
//                incrementerName: String, leastVersion: String
//            ): (VersionIncrementerContext) -> Version {
//                val incrementer: VersionProperties.Incrementer =
//                    PredefinedVersionIncrementer.versionIncrementerFor(incrementerName)
//
//                return { context: VersionIncrementerContext ->
//                    val parsedLeastVersion: Version = Version.valueOf(leastVersion)
//                    if (parsedLeastVersion > context.currentVersion) parsedLeastVersion else incrementer.apply(context)
//                }
//            }
        }

        // Register a task
        project.tasks.register("greeting") { task ->
            task.doLast {
                println("Hello from plugin 'gip-release-new'")
            }
        }
    }
}
