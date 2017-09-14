include conf/machine/include/x86-base.inc

MACHINE_FEATURES += "wifi pcbios"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-stable"

MACHINE_EXTRA_RRECOMMENDS += "linux-firmware eee-acpi-scripts"

GLIBC_ADDONS = "nptl"
