# Glossary

This page defines the core terms used in Yarrow.  

---

### - Alpha  
Normalized progress of a transition — `0` means just started, `1` means complete.

### - Delta  
Time elapsed between the previous and current frame.  
Used to keep updates consistent across frame rates.

### - Lerp  
Linear interpolation between two values based on alpha.  
Used to calculate a value that lies between a start and end point.

For example, if you're moving an object from `x = 100` to `x = 200`,  
and the current alpha is `0.5`, lerp will return `150`.  
When alpha is `0`, it returns the start value; when `1`, it returns the end.

Lerp ensures smooth, gradual transitions over time.

### - Transition  
A smooth change of a single property over time — e.g., position from A to B.

### - Animation  
A sequence of one or more transitions, possibly with delays, easing, and looping.

### - Delay  
Amount of time before a transition or animation starts running.

### - Duration  
Total time it takes for a transition or animation to complete (including delay, repeats, etc...) 

### - Easing  
A function that maps linear alpha to a non-linear curve, changing how the transition progresses over time.

Instead of moving at a constant speed, easing controls acceleration and deceleration — this gives motion a more natural or stylized feel.

For example
- **linear** keeps a constant speed (no easing)
- **ease-in** starts slow, then speeds up  
- **ease-out** starts fast, then slows down  
- **ease-in-out** starts slow, speeds up in the middle, then slows down again  

Easing functions are essential for making animations feel smooth and responsive rather than robotic or mechanical.

### - Loop  
Specifies if and how many times an animation should repeat. Can be finite or infinite.

### - Speed  
Multiplier for animation timing. `2.0` plays it twice as fast, `0.5` half as fast.
