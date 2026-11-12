@echo off
SetLocal ENABLEDELAYEDEXPANSION
FOR %%a in (*) do (
    set "name=%%a"
    set "name=!name: (=_!"
    set "name=!name:)=!"
    if not "%%a"=="!name!" (
        ren "%%a" "!name!"
    )
)
exit