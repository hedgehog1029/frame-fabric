# frame-fabric

![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fmaven.offbeatwit.ch%2Frepository%2Freleases%2Fch%2Foffbeatwit%2Fframe%2Fframe-fabric%2Fmaven-metadata.xml)

[Frame](https://github.com/hedgehog1029/Frame) wrapper for Fabric.

## Getting

In your Fabric mod's build.gradle:

```groovy
modImplementation "ch.offbeatwit.frame:frame-fabric:1.0.4+1.19.2"
```

In your fabric.mod.json:

```json
{
  "depends": {
    "frame-fabric": ">=1.0.4"
  },
  "entrypoints": {
    "frame": ["com.mymod.MyModFrameInitializer"]
  }
}
```

## Using

You should define an entrypoint that extends FrameInitializer, e.g.

```java
package com.mymod;

class MyModFrameInitializer implements FrameInitializer {
    @Override
    public void initFrame(Frame frame) {
        // Now load your modules!
        frame.loadModule(new MyCoolModule());
		
        // You can add injectors or bindings too.
        // Your Frame instance is unique, so you won't clash
        // with other mods.
        frame.loadBindings(new MyCoolBindings());
        frame.addInjector(new MyCoolInjector());
    }
}
```

Have a look at Frame's [developer guide](https://github.com/hedgehog1029/Frame#developer-guide)
for a more thorough walkthrough on working with Frame.
