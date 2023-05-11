package de.gipmbh.gradle.plugins.release

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property

abstract class VersionConfigExtension {

    abstract val version: Property<String>
    abstract val leastVersion: Property<String>
    abstract val tagPrefix: Property<String>
    abstract val ignoreUncommittedChanges: Property<Boolean>
    abstract val useHighestVersion: Property<Boolean>
    abstract val incrementer: Property<String>
    abstract val directory: DirectoryProperty

    init {
        tagPrefix.convention("V")
        ignoreUncommittedChanges.convention(false)
        useHighestVersion.convention(false)
    }
}