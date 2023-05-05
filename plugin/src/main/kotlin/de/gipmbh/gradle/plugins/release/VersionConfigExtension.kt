package de.gipmbh.gradle.plugins.release

import org.gradle.api.provider.Property
import java.io.File

abstract class VersionConfigExtension {

    abstract val version: Property<String>
    abstract val leastVersion: Property<String>
    abstract val tagPrefix: Property<String>
    abstract val ignoreUncommittedChanges: Property<Boolean>
    abstract val useHighestVersion: Property<Boolean>
    abstract val incrementer: Property<String>
    abstract val gitDirectory: Property<File>

    init {
        tagPrefix.convention("V")
    }
}