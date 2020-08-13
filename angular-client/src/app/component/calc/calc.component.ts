import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-calc',
  templateUrl: './calc.component.html',
  styleUrls: ['./calc.component.css']
})
export class CalcComponent implements OnInit {

  // Datos
  anchoPliego: number;
  largoPliego: number;
  anchoCorte: number;
  largoCorte: number;

  // Resultados
  ejemplaresPorPliego: number;
  horizontales: number;
  verticales: number;
  totalPliegos: number;
  totalEjemplares: number;
  porcentajeUtilizado: number;
  porcentajeSinUtilizar: number;

  constructor() {
    console.log("calc.component - constructor");

    this.ejemplaresPorPliego = 0;
    this.horizontales = 0;
    this.verticales = 0;
    this.totalPliegos = 0;
    this.totalEjemplares = 0;
    this.porcentajeUtilizado = 0;
    this.porcentajeSinUtilizar = 0;
  }

  ngOnInit(): void {
    console.log("calc.component - ngOnInit");
  }

  /**
   * Clic en el botón calcular
   */
  clic(): void {
    console.log("calc.component - calcular");

    let b = Math.max(this.anchoPliego, this.largoPliego);
    console.log("b = " + b);
    let h = Math.min(this.anchoPliego, this.largoPliego);
    console.log("h = " + h);
    let cb = Math.max(this.anchoCorte, this.largoCorte);
    console.log("cb = " + cb);
    let ch = Math.min(this.anchoCorte, this.largoCorte);
    console.log("ch = " + ch);

    let a1h = h;
    console.log("a1h = " + a1h);
    let a1b = b;
    console.log("a1b = " + a1b);
    // let a2h, a2b;
    let a2h;
    let a2b;
    console.log("a2h = " + a2h);
    console.log("a2b = " + a2b);
    let sumaCortes = 0;
    console.log("sumaCortes = " + sumaCortes);
    let corteA1, corteA2;
    console.log("corteA1 = " + corteA1);
    console.log("corteA2 = " + corteA2);
    let totalCortes;
    console.log("totalCortes = " + totalCortes);
    var acomodo1, acomodo2;/* = {
      a1b: '',
      a2b: '',
      a1h: '',
      a2h: '',
      sumaCortes: ''
    };*/
    console.log("acomodo1 = " + acomodo1);
    console.log("acomodo2:");
    console.log(acomodo2);

    /* Primero se acomoda el papel en H */
    let cortes = this.acomoda(b, h, 'H', 'M');
    console.log("cortes:");
    console.log(cortes);

    totalCortes = cortes.cortesT;
    acomodo1 = {
      a1b: b,
      a2b: b,
      a1h: h,
      a2h: 0,
      sumaCortes: totalCortes,
      cortesH1: cortes.cortesH,
      cortesB1: cortes.cortesB,
      cortesT1: cortes.cortesT,
      cortesH2: 0,
      cortesB2: 0,
      cortesT2: 0
    };

    for (let index = 0; index <= cortes.cortesH; index++) {
      console.log("calc.component - calcular - for1");

      a2b = b;

      a2h = parseFloat(((ch * index) + cortes.sobranteH).toFixed(2));
      a1h = parseFloat((h - a2h).toFixed(2));

      corteA1 = this.acomoda(a1b, a1h, 'H', 'N');
      corteA2 = this.acomoda(a2b, a2h, 'V', 'N');

      sumaCortes = corteA1.cortesT + corteA2.cortesT;

      if (sumaCortes > totalCortes) {
        console.log("calc.component - calcular - for1 - if");

        totalCortes = sumaCortes;
        acomodo1 = {
          a1b: a1b,
          a2b: a2b,
          a1h: a1h,
          a2h: a2h,
          sumaCortes: totalCortes,
          cortesH1: corteA1.cortesH,
          cortesB1: corteA1.cortesB,
          cortesT1: corteA1.cortesT,
          cortesH2: corteA2.cortesH,
          cortesB2: corteA2.cortesB,
          cortesT2: corteA2.cortesT
        };
      }

      console.log(
        "index: " + index +
        " corte A1: " + corteA1.cortesT +
        " corte A2: " + corteA2.cortesT +
        " cortes: " + sumaCortes);
    }

    // console.log("acomodo2.sumaCortes = " + acomodo2.sumaCortes);

    totalCortes = cortes.cortesT;
    acomodo2 = {
      a1b: b,
      a2b: 0,
      a1h: h,
      a2h: h,
      sumaCortes: totalCortes,
      cortesH: totalCortes,
      cortesV: 0
    };

    for (let index = 0; index <= cortes.cortesB; index++) {
      console.log("calc.component - clic - for2");

      a2h = h;
      a1h = h;

      a2b = parseFloat(((cb * index) + cortes.sobranteB).toFixed(2));
      a1b = parseFloat((b - a2b).toFixed(2));

      corteA1 = this.acomoda(a1b, a1h, 'H', 'N');
      corteA2 = this.acomoda(a2b, a2h, 'V', 'N');

      sumaCortes = corteA1.cortesT + corteA2.cortesT;

      if (sumaCortes > totalCortes) {
        console.log("calc.component - clic - for2 - if");

        totalCortes = sumaCortes;
        acomodo2 = {
          a1b: a1b,
          a2b: a2b,
          a1h: a1h,
          a2h: a2h,
          sumaCortes: totalCortes,
          cortesH1: corteA1.cortesH,
          cortesB1: corteA1.cortesB,
          cortesT1: corteA1.cortesT,
          cortesH2: corteA2.cortesH,
          cortesB2: corteA2.cortesB,
          cortesT2: corteA2.cortesT
        };
      }
      console.log(
        "index: " + index +
        " corte A1: " + corteA1.cortesT +
        " corte A2: " + corteA2.cortesT +
        " cortes: " + sumaCortes);
    }

    console.log("acomodo2.sumaCortes = " + acomodo2.sumaCortes);


    if (acomodo2.sumaCortes > acomodo1.sumaCortes) {
      // calculando el area
      this.calcularArea(b, h, cb, ch, acomodo2.sumaCortes);
      this.calcular(
        b, h,
        acomodo2.cortesT2, acomodo2.cortesT1,
        acomodo2.sumaCortes, acomodo2.sumaCortes,
        'M')
    } else {
      this.calcularArea(b, h, cb, ch, acomodo1.sumaCortes);
      this.calcular(
        b, h,
        acomodo1.cortesT2, acomodo1.cortesT1,
        acomodo1.sumaCortes, acomodo1.sumaCortes,
        "M");

    }
  }

  private calcular(
    b, h, cortesV, cortesH, totalCortes, utilizables, orientacion
  ) {
    let cortesDeseados = 0;
    let pliegosP = 1;
    let pliegos = 0;

    if (orientacion === 'H') {
      pliegos = Math.ceil(cortesDeseados / utilizables);
    } else if (orientacion === 'V') {
      pliegos = Math.ceil(cortesDeseados / utilizables);
    } else {
      // calculando el número de pliegos necesarios
      pliegos = Math.ceil(cortesDeseados / totalCortes);
    }

    if (pliegos !== 0 && isNaN(pliegos)) {
      pliegosP = pliegos;
    } else if (isNaN(pliegos)) {
      pliegos = 0;
    }

    // calculando el número total de cortes en todos los pliegos

    let noTotalCortes = totalCortes * pliegos;

    this.ejemplaresPorPliego = totalCortes;
    this.horizontales = cortesH;
    this.verticales = cortesV;
    this.totalPliegos = pliegos;
    this.totalEjemplares = noTotalCortes;

  }

  private acomoda(d1, d2, acomodoCorte, acomodoPliego) {
    /**
     * anchoCorte y largoCorte siempre son constantes
     */
    console.log("calc.component - acomoda");
    let anchoCorte = this.anchoCorte;
    console.log("anchoCorte = " + anchoCorte);
    let largoCorte = this.largoCorte;
    console.log("largoCorte = " + largoCorte);
    let cb = 1;
    let ch = 1;
    let b = 1;
    let h = 1;

    /**
     * Pliego
     */
    if (acomodoPliego === 'V') {
      b = Math.min(d1, d2);
      h = Math.max(d1, d2);
    } else if (acomodoPliego === 'H') {
      /**
       * Acomodo del pliego en horizontal y para el cálculo del máximo
       */
      b = Math.max(d1, d2);
      h = Math.min(d1, d2);
    } else {
      b = d1;
      h = d2;
    }

    /**
     * Corte
     */
    if (acomodoCorte === 'H') {
      cb = Math.max(anchoCorte, largoCorte);
      ch = Math.min(anchoCorte, largoCorte);
    } else if (acomodoCorte === 'V') {
      cb = Math.min(anchoCorte, largoCorte);
      ch = Math.max(anchoCorte, largoCorte);
    } else {
      cb = anchoCorte;
      ch = largoCorte;
    }

    /**
     * Cálculo
     */
    console.log("b / cb = " + Math.floor(b / cb));
    console.log("b / cb = " + parseInt((b / cb).toString()));
    let cortesT = Math.floor(b / cb) * Math.floor(h / ch);
    let cortesB = Math.floor(b / cb);
    let cortesH = Math.floor(h / ch);
    let sobranteB = parseFloat((b - (cortesB * cb)).toFixed(2));
    let sobranteH = parseFloat((h - (cortesH * ch)).toFixed(2));
    let areaUtilizada = parseFloat(((cb * ch) * (b / cb) * (h / ch)).toFixed(2));

    /**
     * Guardar disposición y resultados
     */
    let cortes = {
      cortesT: cortesT,
      cortesB: cortesB,
      cortesH: cortesH,
      sobranteB: sobranteB,
      sobranteH: sobranteH,
      areaUtilizada: areaUtilizada
    };

    return cortes;
  }

  private calcularArea(
    anchoPapel: number, largoPapel: number,
    anchoCorte: number, largoCorte: number,
    cortesEnPliego: number
  ): void {
    console.log("calc.component - calcularArea");

    let areaPapel = anchoPapel * largoPapel;
    let areaCorte = anchoCorte * largoCorte;

    let areaUtilizadaCortes = cortesEnPliego * areaCorte;

    let porcentajeAreaUtilizada = ((areaUtilizadaCortes * 100) / areaPapel).toFixed(2);
    let porcentajeAreaInutilizada = (100 * Number(porcentajeAreaUtilizada)).toFixed(2);

    this.porcentajeUtilizado = Number(porcentajeAreaUtilizada);
    this.porcentajeSinUtilizar = Number(porcentajeAreaInutilizada);
  }

}
