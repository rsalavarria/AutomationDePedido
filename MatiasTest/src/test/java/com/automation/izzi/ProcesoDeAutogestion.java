package com.automation.izzi;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProcesoDeAutogestion {
	
	private WebDriver driver;
	private WebDriverWait wait;
	public int tiempo = 2000;
	//************************LEER*****************************************************************
	// En eclipse para ir al desarrollo del metodo debo hacer CTRL + Click al llamamiento del mismo.
	// En algunos casos hay metodos que estan comentados, en caso de querer cambiar las elecciones solo basta con descomentar uno y comentar el otro.
	
	@Before
	public void setUp() throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("https://sittest-izzimx.cs125.force.com/portal");
		driver.findElement ( By.id ("username")). sendKeys ("lsalas_community@yopmail.com.sittest");
		driver.findElement ( By.id ("password")). sendKeys ("izzi12345");
		driver.findElement (By.xpath ("// input [@ class = 'button r4 wide primary']")). click ();
		Thread.sleep(5000);
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 40);
		Thread.sleep(15000);
	}
		

		
	@Test
	public void Main () throws InterruptedException {
		StepiniciarContratacion(driver);
		SeleccionDelPlan(driver);
		StepDispositivos(0);
		StepValidacionDeDispositivos(0);
		StepPortabilidad(0);
		StepTipoDeEntrega(0);
		StepResumenDeCompra();
		
		
	}
		
	/**
	 * Iniciar la Contratacion
	 * 
	 * @throws InterruptedException
	 */
	public static void StepiniciarContratacion (WebDriver driver)throws InterruptedException {
		
		driver.switchTo().frame(0);
		new WebDriverWait (driver, 20)
		.until(ExpectedConditions.invisibilityOfElementLocated(By.className("slds-spinner_container")));
		driver.findElement (By.xpath ("// button [@ class ='slds-button slds-button_brand btnCommunity']")). click ();
		driver.switchTo().defaultContent();
	}
	
	
	/**
	 * Selecciona el plan en el paso Planes.
	 * 
	 * @throws InterruptedException
	 */
	public static void SeleccionDelPlan(WebDriver driver) throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iFrameResizer1")));
		WebElement frame = driver.findElement(By.id("iFrameResizer1"));
		driver.switchTo().frame(frame);	
		Thread.sleep(2000);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("slds-spinner_container")));
		WebElement seleccionar = wait.until(ExpectedConditions.elementToBeClickable(By.id("block_01tc0000007pvuiAAA")));
		Thread.sleep(1000);
		seleccionar.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("slds-spinner_container")));
		WebElement siguiente = wait.until(ExpectedConditions.elementToBeClickable(By.id("PlanSelection_nextBtn")));
		while(siguiente.isEnabled() && siguiente.isDisplayed()) {
			Thread.sleep(1000);
			siguiente.click();
		}
		Thread.sleep(10000);
	}

	
	/**
	 * Selecciona entre usar un dispositivo propio o adquirir uno nuevo
	 * 
	 * @param index			0 = "Dispositivo propio" || 1 = "Compra de dispositivo"
	 * @throws InterruptedException
	 */
	public void StepDispositivos(int index) throws InterruptedException {
		WaitForInvisibleSpinner();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("RadioDevices")));
		List<WebElement> optTipoDeDispositivo = driver.findElements(By.id("RadioDevices"));
		optTipoDeDispositivo.get(index).findElement(By.xpath("./..")).click();//Trae Tu equipo a Izzi
		Thread.sleep(2000);
		driver.findElement(By.id("StepDevicesSelect_nextBtn")).click();
		Thread.sleep(5000);
		if (index == 1) {
			StepSeleccionDeDispositivo();
			// Selecciona el check que indica que el cliente no esta interesado en estos equipos.
			// OptDesinteresEquipo();
		}
	}
	
	/**
	 * Selecciona un dispositivo en el paso Seleccion de Dispositivo.
	 * 
	 * @throws InterruptedException
	 */
	public void StepSeleccionDeDispositivo() throws InterruptedException {
		WaitForInvisibleSpinner();
		WebElement optDispositivo = wait.until(ExpectedConditions.elementToBeClickable(By.id("block_01t3K000000HEDoQAO")));
		optDispositivo.findElement(By.xpath("./..")).click();
		Thread.sleep(2000);
		
		WaitForInvisibleSpinner();
		driver.findElement(By.id("vlcCart_Top")).findElement(By.xpath(".//div[1]")).click();
		WebElement btnSiguiente = driver.findElement(By.id("StepChooseDevices_nextBtn"));
		while(btnSiguiente.isEnabled() && btnSiguiente.isDisplayed()) {
			Thread.sleep(1000);
			btnSiguiente.click();
		}
		Thread.sleep(2000);
	}

	/**
	 * Selecciona el metodo de validacion de compatibilidad con Izzi.
	 * 
	 * @param index			0 = "IMEI" || 1 = "Dispositivo"
	 * @throws InterruptedException
	 */
	public void StepValidacionDeDispositivos(int index) throws InterruptedException {
		WaitForInvisibleSpinner();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("RadioSelectMethod")));
		List<WebElement> optMetodoDeValidacion = driver.findElements(By.id("RadioSelectMethod"));
		Thread.sleep(tiempo);
		optMetodoDeValidacion.get(index).findElement(By.xpath("./..")).click();
		Thread.sleep(tiempo);
		
		if (index == 0)
			OptValidacionPorImei();
		else
			// Se debe cambiar al false para no seleccionar dispositivos de la lista
			OptValidacionPorDispositivo(true);
	}
	
	/**
	 * Realiza la validacion por IMEI.
	 * 
	 * @throws InterruptedException
	 */
	public void OptValidacionPorImei() throws InterruptedException {
		// Imei valido
		driver.findElement(By.xpath("//input[@id=\'NumberIMEI\']")).sendKeys("355576090532168");
		
		// Imei invalido
		// driver.findElement(By.xpath("//input[@id=\'NumberIMEI\']")).sendKeys("000000000000000");
		WebElement btnValidar = driver.findElement(By.xpath("//div[@id=\'IPAValidateIMEI\']/p"));
		btnValidar.click();
		Thread.sleep(tiempo);

		WaitForInvisibleSpinner();
		List<WebElement> buy = driver.findElements(By.id("RadioBuyDevices"));
		
		boolean optVerEquiposCompatibles = false;
		if (buy.get(0).isEnabled() && buy.get(0).isDisplayed()) {
			optVerEquiposCompatibles = true;
		}
		
		driver.findElement(By.xpath("//div[@id='StepApprovedDevice_nextBtn']")).click();
		Thread.sleep(tiempo);
		
		if (optVerEquiposCompatibles)
			StepSeleccionDeDispositivo();
	}
	
	/**
	 * Realiza la validacion por dispositivo. 
	 * Toma el parametro booleano isValid el cual establece si se selecciona un dispositivo de la lista 
	 * o si por el contrario se envia al paso Seleccionar dispositivo
	 * 
	 * @param isValid			true = "Selecciona un dispositivo de la lista" || false = "Habilita la opcion 'seleccion de dispositivo'"
	 * @throws InterruptedException
	 */
	public void OptValidacionPorDispositivo(boolean isValid) throws InterruptedException {
		boolean optVerEquiposCompatibles = false;
		
		// Si el parametro es false:
		if (!isValid) {
			// El flujo va a seleccion de dispositivo
			WebElement chkDispositivoNoEncontrado = driver.findElement(By.xpath("//input[@id=\'CheckCompatibility\']"));
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].style.display = 'block'; "
					+ "arguments[0].style.zIndex = '999999'; "
					+ "arguments[0].click()", chkDispositivoNoEncontrado);
			
			optVerEquiposCompatibles = true;
			
		// En caso contrario selecciona un equipo de la lista y continua a portabilidad
		}else {
			// Selecciona la marca
			driver.findElement(By.xpath("//select[@id=\'SelectBrand\']")).click();
			driver.findElement(By.xpath("//option[@label='BITTIUM']")).click();
			
			// Selecciona el modelo
			driver.findElement(By.xpath("//select[@id=\'SelectModel\']")).click();
			driver.findElement(By.xpath("//option[@label='Tough Mobile']")).click();
		}
		
		driver.findElement(By.xpath("//div[@id='StepApprovedDevice_nextBtn']")).click();
		Thread.sleep(tiempo);
		
		if (optVerEquiposCompatibles)
			StepSeleccionDeDispositivo();
	}
		
	/**
	 * Selecciona la opcion de portabilidad.
	 * 
	 * @param index			0 = "SI" || 1 = "NO"
	 * @throws InterruptedException
	 */
	public void StepPortabilidad(int index) throws InterruptedException {
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("OptionPortability")));
		Thread.sleep(1000);
		
		List<WebElement> optPortarNumeroActual = driver.findElements(By.id("OptionPortability"));
		optPortarNumeroActual.get(index).findElement(By.xpath("./..")).click();
		Thread.sleep(tiempo);
		
		WebElement btnSiguiente = driver.findElement(By.xpath("//*[@id=\'StepDeviceValidation_nextBtn\']/p"));
		while(btnSiguiente.isEnabled() && btnSiguiente.isDisplayed()) {
			Thread.sleep(1000);
			btnSiguiente.click();
		}
		Thread.sleep(tiempo);
	}
	
	/**
	 * Selecciona el tipo de entrega en el paso Tipo de Entrega
	 * 
	 * @param index			0 = "Entrega en Suscursal" || 1 = "Entrega en Domicilio"
	 * @throws InterruptedException
	 */
	public void StepTipoDeEntrega(int index) throws InterruptedException {
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("RadioProfileNoVentas")));
		List<WebElement> optTipoDeEntrega = driver.findElements(By.xpath("//input[@id='RadioProfileNoVentas']"));
		Thread.sleep(2000);
		
		// Si  la entrega es en Sucursal:
		if (index == 0) {
			optTipoDeEntrega.get(index).findElement(By.xpath("./..")).click();
			Thread.sleep(2000);
			List<WebElement> stock = driver.findElements(By.xpath("//span[@class='slds-radio--faux ng-scope']"));
			//driver.findElement(By.id("RadioRetiroOtraSucursal|0")).click();//ng-form[@id='RadioRetiroOtraSucursal|0']
			
			//Verifica si se puede seleccionar una sucursal
			if (stock.get(stock.size()-2).isEnabled()) { 
				stock.get(stock.size()-2).click();
				
				//selecciona la sucursal "ATIZAPAN"
				driver.findElement(By.xpath("//*[@id=\'SelectSucursal\']/option[3]")).click(); 
				Thread.sleep(1000);
				
				//selecciona el boton validar
				driver.findElement(By.xpath("//div[@id=\'WrapperCheckDeviceStockSucursal\']")).click(); 
			}
			Thread.sleep(2000);
			
		//En caso contrario, la entrega es en Domicilio
		} else { 
			optTipoDeEntrega.get(index).findElement(By.xpath("./..")).click();
			Thread.sleep(2000);
		}
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("StepSaleProcessDevice_nextBtn")));
		driver.findElement(By.id("StepSaleProcessDevice_nextBtn")).click();
		Thread.sleep(5000);
	}
	
	
	/**
	 * Este metodo es el paso final de la gestion de compra, donde se muestra el resumen y pasa a la siguiente pantalla de finalizar compra
	 * 
	 * @throws InterruptedException
	 */
	public void StepResumenDeCompra() throws InterruptedException {
		WaitForInvisibleSpinner();
		WebElement btnSiguiente = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id=\'DeliveryHomeSummary_nextBtn\']/p")));
		while(btnSiguiente.isDisplayed() && btnSiguiente.isEnabled()) {
			Thread.sleep(1000);
			btnSiguiente.click();
		}
		Thread.sleep(tiempo);
		
		WaitForInvisibleSpinner();
		WebElement btnFinish = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class=\'slds-button slds-button_brand ng-binding\']")));
		btnFinish.click();
		Thread.sleep(tiempo);
		//Nos muestra el numero de pedido
	}

	/**
	 * Selecciona el check que indica que el cliente no esta interesado en ningun equipo.
	 * 
	 * @throws InterruptedException
	 */
	public void OptDesinteresEquipo() throws InterruptedException {
		driver.findElement(By.id("CheckboxDontWantDevice")).findElement(By.xpath("./..")).click();
		Thread.sleep(tiempo);
		driver.findElement(By.id("StepChooseDevices_nextBtn")).click();
		Thread.sleep(tiempo);
		driver.findElement(By.xpath("slds-button slds-button_brand ng-binding")). click();
		Thread.sleep(tiempo);
		
	}	
	
	/**
	 * Retrasa la ejecucion hasta que spinner sea invisible
	 */
	public void WaitForInvisibleSpinner() {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("slds-spinner_container")));
	}
	
	
}
