//头像裁剪上传部分
function Avatar(){
  this.scale = 1;
  this.canvasWH = {width:150,height:150};
  this.cutWH = 120;
  //获取选择的图片文件以及进行数据转换
  this.file = null;
  this.image = new Image();
  this.fileReader = new FileReader();
  //左右两侧的canvas， 一个进行裁剪，另一个预览
  this.originCanvas = document.createElement('canvas');
  this.originCtx = this.originCanvas.getContext('2d');
  this.endCanvas = document.createElement('canvas');
  this.endCtx = this.endCanvas.getContext('2d');
  //裁剪后的图片的base64数据存放
  this.endDataURL = null;
  //图片转换成blob
  this.blob = null;
  this.control = false;
  //遮罩层
  this.mask = document.querySelector('#mask');
  this.maskPos = {top:0,left:0,right:0,bottom:0};
  this.draggable = false;
  this.resizable = false;
}
Avatar.prototype = {
  init: function(){
    var _this = this;

    this.initDragMove();
    this.initResize();
    this.refreshMaskPos();

    this.endCanvas.width = this.endCanvas.height = this.cutWH;

    this.originCanvas.id = 'all';
    this.endCanvas.id = 'show';

    this.file = document.getElementById('avatar-src').files[0];
    this.fileReader.onload = function(){
      _this.image.src = _this.fileReader.result;
    };

    this.mask.style.width = this.mask.style.height = this.mask.clientHeight + "px";

    this.fileReader.readAsDataURL(this.file);
    this.image.onload = function () {
      _this.originCanvas.width = _this.canvasWH.width;
      _this.scale = 150/_this.image.width;
      _this.originCanvas.height = _this.image.height*_this.scale;
      _this.canvasWH.height = _this.originCanvas.height>150? _this.originCanvas.height:150;
      _this.drawOriginCanvas();
      _this.drawEndCanvas();
    }
  },
  drawOriginCanvas: function(){
    this.originCtx.drawImage(this.image,0,0,this.canvasWH.width,this.image.height*this.scale);
    document.getElementById('avatar-all').replaceChild(this.originCanvas,document.getElementById('avatar-all').getElementsByTagName('canvas')[0]);
  },
  drawEndCanvas: function(){
    this.endCtx.clearRect(0,0,120,120);
    this.endCtx.drawImage(this.image,(this.maskPos.left/this.originCanvas.width)*this.image.width,(this.maskPos.top/this.originCanvas.height)*this.image.height,(this.cutWH/this.originCanvas.width)*this.image.width,(this.cutWH/this.originCanvas.height)*this.image.height,0,0,120,120);
    document.getElementById('preview-show').replaceChild(this.endCanvas,document.getElementById('preview-show').getElementsByTagName('canvas')[0]);
    this.canvasToBlob();
  },
  canvasToBlob:function(){
    var _this = this;
    this.endDataURL = this.endCanvas.toDataURL('image/png');
    this.endCanvas.toBlob(function(blob){
      _this.blob = blob;
      _this.control = true;
    },'image/png');
  },
  refreshMaskPos:function(){
    this.maskPos.top = this.mask.offsetTop;
    this.maskPos.left = this.mask.offsetLeft;
    this.maskPos.right = this.canvasWH.width - this.mask.offsetLeft - this.cutWH;
    this.maskPos.bottom = this.canvasWH.height - this.mask.offsetTop - this.cutWH;
    this.cutWH = this.mask.offsetWidth;
  },
  initDragMove:function(){
    var _this = this;
    var pointerX;
    var pointerY;
    this.mask.addEventListener('mousedown',function(e){
      if(!e.target.classList.contains('rect')){
        _this.draggable = true;
        pointerX = e.pageX;
        pointerY = e.pageY;
      }
    });
    this.mask.addEventListener('mousemove',function(e){
      if(_this.draggable){
        var nowPageX = e.pageX;
        var nowPageY = e.pageY;
        var toMove = {left:nowPageX-pointerX,top:nowPageY-pointerY};

        if (_this.maskPos.left+toMove.left>0&&_this.maskPos.left+toMove.left<_this.originCanvas.width-_this.cutWH) {
          _this.mask.style.left = _this.maskPos.left+toMove.left+'px';
          _this.refreshMaskPos();
          _this.drawEndCanvas();
          pointerX=nowPageX;
        }
        if (_this.maskPos.top+toMove.top>0&&_this.maskPos.top+toMove.top<(_this.originCanvas.height>=120? _this.originCanvas.height:150)-_this.cutWH) {
          _this.mask.style.top = _this.maskPos.top+toMove.top+'px';
          _this.refreshMaskPos();
          _this.drawEndCanvas();
          pointerY=nowPageY;
        }
      }
    });
    document.addEventListener('mouseup',function(e){
      _this.draggable = false;
    })
  },
  initResize:function(){
    var _this = this;
    var pointerX;
    var pointerY;
    var tars = document.querySelectorAll('.rect');
    var id = '';
    elesAddEvent(tars,'mousedown',function(e){
      document.body.style.cursor = 'se-resize';
      _this.resizable = true;
      pointerX = e.pageX;
      pointerY = e.pageY;
      id = e.target.id;
      _this.fixPos(id);
    });
    eleAddEvent(document,'mousemove',function(e){
      if(_this.resizable) {
        var nowPageX = e.pageX;
        var nowPageY = e.pageY;
        var toMove = {left: nowPageX - pointerX, top: nowPageY - pointerY};
        if ((id === 'lt' || id === 'rb') && (toMove.left * toMove.top < 0)) {
          return 0;
        }
        if ((id === 'rt' || id === 'lb') && (toMove.left * toMove.top > 0)) {
          return 0;
        }
        // var moveDis = Math.abs(toMove.left)<Math.abs(toMove.top)? toMove.top:toMove.left;
        // switch (id) {
        //   case 'lt':
        //     _this.mask.style.width = _this.mask.style.height = _oriHeight - moveDis + "px";
        //     break;
        //   case 'rb':
        //     _this.mask.style.width = _this.mask.style.height = _oriHeight + moveDis + "px";
        //     break;
        //   case 'lb':
        //     _this.mask.style.width = _this.mask.style.height = _oriHeight - moveDis + "px";
        //     break;
        //   case 'rt':
        //     _this.mask.style.width = _this.mask.style.height = _oriHeight + moveDis + "px";
        //     break;
        //   default:
        //     break;
        // }

        _this.fixSize(id,toMove);
        _this.refreshMaskPos();
        _this.drawEndCanvas();
        pointerX = nowPageX;
        pointerY = nowPageY;
      }
    });
    eleAddEvent(document,'mouseup',function(e){
      _this.resizable = false;
      document.body.style.cursor = '';
    });
  },
  fixPos:function(direction){
    switch (direction) {
      case 'lt':
        this.mask.style.left = this.mask.style.top = "auto";
        this.mask.style.right = this.maskPos.right+'px';
        this.mask.style.bottom = this.maskPos.bottom+'px';
        break;
      case 'rb':
        this.mask.style.right = this.mask.style.bottom = "auto";
        this.mask.style.left = this.maskPos.left+'px';
        this.mask.style.top = this.maskPos.top+'px';
        break;
      case 'lb':
        this.mask.style.left = this.mask.style.bottom = "auto";
        this.mask.style.right = this.maskPos.right+'px';
        this.mask.style.top = this.maskPos.top+'px';
        break;
      case 'rt':
        this.mask.style.right = this.mask.style.top = "auto";
        this.mask.style.left = this.maskPos.left+'px';
        this.mask.style.bottom = this.maskPos.bottom+'px';
        break;
      default:
        break;
    }
  },
  fixSize:function(id,toMove){
    var left = toMove.left;
    var top = toMove.top;
    var moveDis = Math.abs(toMove.left)<Math.abs(toMove.top)? Math.abs(toMove.top):Math.abs(toMove.left);
    var oriHeight = parseInt(this.mask.style.height);
    switch (id) {
      case 'lt':
        if(left<=0&&top<=0){
          this.mask.style.width = this.mask.style.height = oriHeight  + moveDis + "px";
        }else{
          this.mask.style.width = this.mask.style.height = oriHeight - moveDis + "px";
        }
        break;
      case 'rb':
        if(left<=0&&top<=0){
          this.mask.style.width = this.mask.style.height = oriHeight - moveDis + "px";
        }else{
          this.mask.style.width = this.mask.style.height = oriHeight + moveDis + "px";
        }
        break;
      case 'lb':
        if(left<=0&&top>=0){
          this.mask.style.width = this.mask.style.height = oriHeight + moveDis + "px";
        }else{
          this.mask.style.width = this.mask.style.height = oriHeight - moveDis + "px";
        }
        break;
      case 'rt':
        if(left<=0&&top>=0){
          this.mask.style.width = this.mask.style.height = oriHeight - moveDis + "px";
        }else{
          this.mask.style.width = this.mask.style.height = oriHeight + moveDis + "px";
        }
        break;
      default:
        break;
    }
  }
};



function eleAddEvent(element,method,func,mode){
  if(document.addEventListener){
    element.addEventListener(method,func,mode);
  }else{
    element.attachEvent(method,func,mode);
  }
}
function elesAddEvent(elements,method,func,mode){
  for(var i=0;i<elements.length;i++){
    eleAddEvent(elements[i],method,func,mode);
  }
}


window.onload = function(){
  var avatar;
  document.querySelector('#avatar-src').addEventListener('change',function(e){
    avatar = new Avatar();
    avatar.init();
  })

}
