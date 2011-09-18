
var data;

var start = [];

var levels =
   [[0, 0, 0, 0],
    [0, 0, 0, 0],
    [0, 0, 0, 0],
    [0, 0, 0, 0]];

for (var i = 0; i < 4; i++){
    for (var j = 0; j < 4; j++){
        start.push([i,j])
        levels[i][j] = 4*i + j
    }
}

var selectData =
   [[1, 1, 1, 1],
    [1, 1, 1, 1],
    [1, 1, 1, 1],
    [1, 1, 1, 1]];

var levelData =
  [
   [
    [4, 5, 4, 5, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [5, 4, 5, 4, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [4, 5, 6, 5, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2],
    [5, 4, 5, 4, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [4, 5, 4, 5, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
   ],
   [
    [4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5],
    [4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1],
    [4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
    [4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1]
   ],
   [
    [7, 0, 7, 1],
    [0, 1, 7, 0],
    [1, 0, 0, 2],
    [0, 7, 1, 0]
   ],
   [
    [0, 1, 0, 1, 3, 0],
    [1, 6, 3, 6, 0, 3],
    [0, 1, 0, 1, 3, 0]
   ],
   [
    [0, 0, 4, 0],
    [4, 0, 0, 1],
    [0, 0, 3, 0],
    [4, 1, 0, 0]
   ],
   [
    [0, 5, 0, 5],
    [1, 0, 0, 0],
    [0, 0, 0, 3],
    [0, 5, 1, 0]
   ],
   [
    [1, 0, 2, 1], 
    [0, 2, 1, 2],
    [2, 1, 2, 0],
    [1, 2, 0, 0]
   ],
   [
    [0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
    [0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0],
    [0, 5, 6, 5, 0, 0, 0, 0, 0, 5, 6, 5, 0],
    [0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0],
    [0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0]
   ],
   [[0, 1, 0, 0, 1],
   [0, 0, 0, 2, 0],
   [1, 1, 6, 0, 1], 
   [0, 0, 6, 1, 0],
   [3, 1, 0, 0, 1],
   [0, 0, 1, 1, 0]],
   [[1, 0, 1, 1],
    [2, 6, 3, 1],
    [1, 3, 6, 2],
    [1, 1, 0, 1]],
  [[1, 3, 1, 3, 0, 3, 0, 3, 1, 1],
    [0, 3, 1, 3, 1, 3, 1, 3, 1, 0]],
   [[4, 4, 4, 4, 4],
    [4, 1, 1, 1, 1],
    [4, 1, 4, 4, 4],
    [4, 1, 4, 2, 4],
    [4, 1, 4, 4, 1]],
   [[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0],
    [0, 4, 6, 4, 0, 0, 0, 0, 0, 4, 6, 4, 0],
    [0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0],
    [0, 4, 6, 4, 0, 0, 0, 0, 0, 4, 6, 4, 0],
    [0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]],
   [[1, 5, 1, 5, 1],
    [5, 5, 5, 5, 5],
    [1, 5, 2, 5, 1],
    [5, 5, 5, 5, 5],
    [1, 5, 1, 5, 1]],
   [[7, 1, 7],
    [1, 0, 1],
    [1, 0, 1],
    [3, 6, 3],
    [1, 3, 1]],
  [[0, 1, 0, 3, 0, 1, 0],
   [1, 6, 1, 6, 1, 6, 1],
   [0, 3, 0, 1, 0, 3, 0],
   [3, 6, 3, 0, 3, 6, 3],
   [0, 1, 0, 1, 0, 1, 0],
   [1, 6, 1, 6, 1, 6, 1],
   [0, 1, 0, 1, 0, 1, 0]],
   [[0, 3, 0],
    [6, 0, 7],
    [6, 0, 7]],
   [[5, 0, 0, 0, 0, 0, 0, 0, 0],
    [5, 0, 0, 6, 7, 0, 0, 0, 0],
    [5, 0, 0, 0, 0, 0, 0, 8, 2]]
]
