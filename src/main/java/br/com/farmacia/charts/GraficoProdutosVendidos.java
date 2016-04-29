package br.com.farmacia.charts;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.farmacia.dao.ItemVendaDAO;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class GraficoProdutosVendidos implements Serializable {
	private BarChartModel barModel;

	@PostConstruct
	public void init() {
		createBarModels();
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries produtosVendidos = new ChartSeries();
		try {
			ItemVendaDAO dao = new ItemVendaDAO();
			List<Object[]> lista = dao.quantidadeProdutosVendidos(1);

			for (Object[] linha : lista) {
				Long quantidade = Long.parseLong(linha[1].toString());

				produtosVendidos.set(linha[0].toString(), quantidade);
			}
		} catch (RuntimeException e) {
			Messages.addGlobalInfo("Erro ao gerar grafico de produtos vendidos");
		}

		model.addSeries(produtosVendidos);

		return model;
	}

	private void createBarModels() {
		createBarModel();
	}

	private void createBarModel() {
		barModel = initBarModel();

		barModel.setTitle("Grafico de produtos vendidos");
		barModel.setLegendPosition("ne");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Produto");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Quantidade");
		yAxis.setMin(0);
	}
}
