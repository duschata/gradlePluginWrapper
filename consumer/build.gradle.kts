import com.github.zafarkhaja.semver.Version
import pl.allegro.tech.build.axion.release.domain.PredefinedVersionIncrementer
import pl.allegro.tech.build.axion.release.domain.VersionIncrementerContext
import pl.allegro.tech.build.axion.release.domain.properties.VersionProperties
import pl.allegro.tech.build.axion.release.domain.scm.ScmPosition

plugins {
    id("java")
//    id("gip-release-new")
    `maven-publish`
}

val incrementer: Property<String> = project.objects.property()
//incrementer.set("incrementPatch")
val leastVersion: Property<String> = project.objects.property()
leastVersion.set("2.0.0")
val customTag: Property<String> = project.objects.property()
customTag.set("V")

val test: (String, ScmPosition) -> String = { version: String, position: ScmPosition -> "aBranchName"}

scmVersion {
//    branchVersionCreator.put("test", test)

    repository {
        directory.set(project.rootProject.file("../"))
    }
    tag {
        prefix.set(customTag.get())
        initialVersion { _, _ -> leastVersion.get() }
    }
    ignoreUncommittedChanges.set(false) // TODO configurable
    useHighestVersion.set(false) //TODO: configurable

    versionCreator("versionWithBranch")
    if (hasProperty("ignoreUncommitted")) {
        checks { checks.uncommittedChanges.set(true) }
    }

    if (incrementer.isPresent) {
        versionIncrementer(leastVersionIncrementer(incrementer.get(), leastVersion.get()))
    } else {
        versionIncrementer(leastVersionIncrementer("incrementMinor", leastVersion.get()))
        branchVersionIncrementer(
            mapOf(
                "(release/|support/).*" to leastVersionIncrementer(
                    "incrementPatch",
                    leastVersion.get()
                )
            )
        )
    }
}

fun leastVersionIncrementer(incrementerName: String, leastVersion: String): (VersionIncrementerContext) -> Version {
    val incrementer: VersionProperties.Incrementer =
        PredefinedVersionIncrementer.versionIncrementerFor(incrementerName)

    return { context: VersionIncrementerContext ->
        val parsedLeastVersion: Version = Version.valueOf(leastVersion)
        if (parsedLeastVersion > context.currentVersion) parsedLeastVersion else incrementer.apply(context)
    }
}



version = scmVersion.version
group = "de.gipmbh.gradle.plugin.gip-release"

println("version: $version")

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}