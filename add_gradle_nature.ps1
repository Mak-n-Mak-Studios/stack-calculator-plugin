(gc .project) -replace ".javanature</nature>", ".javanature</nature>`n`t`t<nature>org.eclipse.buildship.core.gradleprojectnature</nature>" | Out-File -encoding ASCII .project
