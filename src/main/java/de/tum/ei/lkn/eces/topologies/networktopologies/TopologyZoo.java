package de.tum.ei.lkn.eces.topologies.networktopologies;


import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Objects;

/**
 * Topologies from the Topology Zoo.
 * This is just an enumeration, the Topology itself can then be created with the
 * GmlTopology class.
 *
 * @author Jochen Guck
 * @author Amaury Van Bemten
 */
public enum TopologyZoo {
	AARNET("topologyZoo/Aarnet.gml",true, 19, 48),
	ABILENE("topologyZoo/Abilene.gml",true, 11, 28),
	ABVT("topologyZoo/Abvt.gml",true, 23, 62),
	ACONET("topologyZoo/Aconet.gml",true, 23, 62),
	AGIS("topologyZoo/Agis.gml",true, 25, 60),
	AI3("topologyZoo/Ai3.gml",true, 10, 18),
	AIRTEL("topologyZoo/Airtel.gml",true, 16, 74),
	AMRES("topologyZoo/Amres.gml",true, 25, 48),
	ANS("topologyZoo/Ans.gml",true, 18, 50),
	ARN("topologyZoo/Arn.gml",true, 30, 58),
	ARNES("topologyZoo/Arnes.gml",true, 34, 94),
	ARPANET196912("topologyZoo/Arpanet196912.gml",true, 4, 8),
	ARPANET19706("topologyZoo/Arpanet19706.gml",true, 9, 20),
	ARPANET19719("topologyZoo/Arpanet19719.gml",true, 18, 44),
	ARPANET19723("topologyZoo/Arpanet19723.gml",true, 25, 56),
	ARPANET19728("topologyZoo/Arpanet19728.gml",true, 29, 64),
	ASNETAM("topologyZoo/AsnetAm.gml",true, 65, 158),
	ATMNET("topologyZoo/Atmnet.gml",true, 21, 44),
	ATTMPLS("topologyZoo/AttMpls.gml",true, 25, 114),
	AZRENA("topologyZoo/Azrena.gml",true, 22, 50),
	BANDCON("topologyZoo/Bandcon.gml",false, 22, 56),
	BASNET("topologyZoo/Basnet.gml",true, 7, 12),
	BBNPLANET("topologyZoo/Bbnplanet.gml",true, 27, 56),
	BELLCANADA("topologyZoo/Bellcanada.gml",true, 48, 130),
	BELLSOUTH("topologyZoo/Bellsouth.gml",true, 51, 132),
	BELNET2003("topologyZoo/Belnet2003.gml",true, 23, 86),
	BELNET2004("topologyZoo/Belnet2004.gml",true, 23, 86),
	BELNET2005("topologyZoo/Belnet2005.gml",true, 23, 88),
	BELNET2006("topologyZoo/Belnet2006.gml",true, 23, 88),
	BELNET2007("topologyZoo/Belnet2007.gml",true, 21, 62),
	BELNET2008("topologyZoo/Belnet2008.gml",true, 21, 62),
	BELNET2009("topologyZoo/Belnet2009.gml",true, 21, 62),
	BELNET2010("topologyZoo/Belnet2010.gml",true, 22, 64),
	BEYONDTHENETWORK("topologyZoo/BeyondTheNetwork.gml",true, 53, 130),
	BICS("topologyZoo/Bics.gml",true, 33, 96),
	BIZNET("topologyZoo/Biznet.gml",true, 29, 66),
	BREN("topologyZoo/Bren.gml",true, 37, 76),
	BSONETEUROPE("topologyZoo/BsonetEurope.gml",true, 18, 48),
	BTASIAPAC("topologyZoo/BtAsiaPac.gml",true, 20, 62),
	BTEUROPE("topologyZoo/BtEurope.gml",true, 24, 74),
	BTLATINAMERICA("topologyZoo/BtLatinAmerica.gml",false, 51, 100),
	BTNORTHAMERICA("topologyZoo/BtNorthAmerica.gml",true, 36, 152),
	CANERIE("topologyZoo/Canerie.gml",true, 32, 82),
	CARNET("topologyZoo/Carnet.gml",true, 44, 86),
	CERNET("topologyZoo/Cernet.gml",true, 41, 118),
	CESNET1993("topologyZoo/Cesnet1993.gml",true, 10, 18),
	CESNET1997("topologyZoo/Cesnet1997.gml",true, 13, 24),
	CESNET1999("topologyZoo/Cesnet1999.gml",true, 13, 24),
	CESNET2001("topologyZoo/Cesnet2001.gml",true, 23, 46),
	CESNET200304("topologyZoo/Cesnet200304.gml",true, 29, 66),
	CESNET200511("topologyZoo/Cesnet200511.gml",true, 39, 88),
	CESNET200603("topologyZoo/Cesnet200603.gml",true, 39, 88),
	CESNET200706("topologyZoo/Cesnet200706.gml",true, 44, 102),
	CESNET201006("topologyZoo/Cesnet201006.gml",true, 52, 126),
	CHINANET("topologyZoo/Chinanet.gml",true, 42, 132),
	CLARANET("topologyZoo/Claranet.gml",true, 15, 36),
	COGENTCO("topologyZoo/Cogentco.gml",true, 197, 490),
	COLT("topologyZoo/Colt.gml",true, 153, 382),
	COLUMBUS("topologyZoo/Columbus.gml",true, 70, 170),
	COMPUSERVE("topologyZoo/Compuserve.gml",true, 14, 34),
	CRLNETWORKSERVICES("topologyZoo/CrlNetworkServices.gml",true, 33, 76),
	CUDI("topologyZoo/Cudi.gml",true, 51, 104),
	CWIX("topologyZoo/Cwix.gml",true, 36, 82),
	CYNET("topologyZoo/Cynet.gml",true, 30, 58),
	DARKSTRAND("topologyZoo/Darkstrand.gml",true, 28, 62),
	DATAXCHANGE("topologyZoo/Dataxchange.gml",true, 6, 22),
	DELTACOM("topologyZoo/Deltacom.gml",true, 113, 366),
	DEUTSCHETELEKOM("topologyZoo/DeutscheTelekom.gml",false, 39, 124),
	DFN("topologyZoo/Dfn.gml",true, 58, 174),
	DIALTELECOMCZ("topologyZoo/DialtelecomCz.gml",false, 193, 302),
	DIGEX("topologyZoo/Digex.gml",true, 31, 76),
	EASYNET("topologyZoo/Easynet.gml",true, 19, 58),
	EENET("topologyZoo/Eenet.gml",true, 13, 32),
	ELIBACKBONE("topologyZoo/EliBackbone.gml",true, 20, 60),
	EPOCH("topologyZoo/Epoch.gml",true, 6, 14),
	ERNET("topologyZoo/Ernet.gml",true, 30, 64),
	ESNET("topologyZoo/Esnet.gml",true, 68, 184),
	EUNETWORKS("topologyZoo/Eunetworks.gml",false, 15, 38),
	EVOLINK("topologyZoo/Evolink.gml",true, 37, 90),
	FATMAN("topologyZoo/Fatman.gml",true, 17, 42),
	FCCN("topologyZoo/Fccn.gml",true, 23, 54),
	FORTHNET("topologyZoo/Forthnet.gml",true, 62, 124),
	FUNET("topologyZoo/Funet.gml",true, 26, 62),
	GAMBIA("topologyZoo/Gambia.gml",true, 28, 56),
	GARR199901("topologyZoo/Garr199901.gml",true, 16, 36),
	GARR199904("topologyZoo/Garr199904.gml",true, 23, 50),
	GARR199905("topologyZoo/Garr199905.gml",true, 23, 50),
	GARR200109("topologyZoo/Garr200109.gml",true, 22, 48),
	GARR200112("topologyZoo/Garr200112.gml",true, 24, 52),
	GARR200212("topologyZoo/Garr200212.gml",true, 27, 58),
	GARR200404("topologyZoo/Garr200404.gml",true, 22, 48),
	GARR200902("topologyZoo/Garr200902.gml",true, 54, 142),
	GARR200908("topologyZoo/Garr200908.gml",true, 54, 136),
	GARR200909("topologyZoo/Garr200909.gml",true, 55, 138),
	GARR200912("topologyZoo/Garr200912.gml",true, 54, 136),
	GARR201001("topologyZoo/Garr201001.gml",true, 54, 136),
	GARR201003("topologyZoo/Garr201003.gml",true, 54, 142),
	GARR201004("topologyZoo/Garr201004.gml",true, 54, 142),
	GARR201005("topologyZoo/Garr201005.gml",true, 55, 144),
	GARR201007("topologyZoo/Garr201007.gml",true, 55, 148),
	GARR201008("topologyZoo/Garr201008.gml",true, 55, 148),
	GARR201010("topologyZoo/Garr201010.gml",true, 56, 150),
	GARR201012("topologyZoo/Garr201012.gml",true, 56, 150),
	GARR201101("topologyZoo/Garr201101.gml",true, 56, 152),
	GARR201102("topologyZoo/Garr201102.gml",true, 57, 154),
	GARR201103("topologyZoo/Garr201103.gml",true, 58, 162),
	GARR201104("topologyZoo/Garr201104.gml",true, 59, 166),
	GARR201105("topologyZoo/Garr201105.gml",true, 59, 168),
	GARR201107("topologyZoo/Garr201107.gml",true, 59, 170),
	GARR201108("topologyZoo/Garr201108.gml",true, 59, 170),
	GARR201109("topologyZoo/Garr201109.gml",true, 59, 172),
	GARR201110("topologyZoo/Garr201110.gml",true, 59, 174),
	GARR201111("topologyZoo/Garr201111.gml",true, 60, 174),
	GARR201112("topologyZoo/Garr201112.gml",true, 61, 178),
	GARR201201("topologyZoo/Garr201201.gml",true, 61, 178),
	GBLNET("topologyZoo/Gblnet.gml",true, 8, 14),
	GEANT2001("topologyZoo/Geant2001.gml",true, 27, 76),
	GEANT2009("topologyZoo/Geant2009.gml",true, 34, 104),
	GEANT2010("topologyZoo/Geant2010.gml",true, 37, 116),
	GEANT2012("topologyZoo/Geant2012.gml",true, 40, 122),
	GETNET("topologyZoo/Getnet.gml",true, 7, 16),
	GLOBALCENTER("topologyZoo/Globalcenter.gml",true, 9, 72),
	GLOBENET("topologyZoo/Globenet.gml",true, 67, 226),
	GOODNET("topologyZoo/Goodnet.gml",true, 17, 62),
	GRENA("topologyZoo/Grena.gml",true, 16, 30),
	GRIDNET("topologyZoo/Gridnet.gml",true, 9, 40),
	GRNET("topologyZoo/Grnet.gml",true, 37, 94),
	GTSCE("topologyZoo/GtsCe.gml",true, 149, 386),
	GTSCZECHREPUBLIC("topologyZoo/GtsCzechRepublic.gml",true, 32, 66),
	GTSHUNGARY("topologyZoo/GtsHungary.gml",true, 30, 62),
	GTSPOLAND("topologyZoo/GtsPoland.gml",true, 33, 74),
	GTSROMANIA("topologyZoo/GtsRomania.gml",true, 21, 48),
	GTSSLOVAKIA("topologyZoo/GtsSlovakia.gml",true, 35, 74),
	HARNET("topologyZoo/Harnet.gml",true, 21, 50),
	HEANET("topologyZoo/Heanet.gml",true, 7, 26),
	HIBERNIACANADA("topologyZoo/HiberniaCanada.gml",true, 13, 28),
	HIBERNIAGLOBAL("topologyZoo/HiberniaGlobal.gml",true, 55, 162),
	HIBERNIAIRELAND("topologyZoo/HiberniaIreland.gml",true, 8, 16),
	HIBERNIANIRELAND("topologyZoo/HiberniaNireland.gml",true, 18, 44),
	HIBERNIAUK("topologyZoo/HiberniaUk.gml",true, 15, 30),
	HIBERNIAUS("topologyZoo/HiberniaUs.gml",true, 22, 58),
	HIGHWINDS("topologyZoo/Highwinds.gml",true, 18, 106),
	HOSTWAYINTERNATIONAL("topologyZoo/HostwayInternational.gml",true, 16, 42),
	HURRICANEELECTRIC("topologyZoo/HurricaneElectric.gml",true, 24, 74),
	IBM("topologyZoo/Ibm.gml",true, 18, 48),
	IIJ("topologyZoo/Iij.gml",true, 37, 132),
	IINET("topologyZoo/Iinet.gml",true, 31, 70),
	ILAN("topologyZoo/Ilan.gml",true, 14, 30),
	INTEGRA("topologyZoo/Integra.gml",true, 27, 72),
	INTELLIFIBER("topologyZoo/Intellifiber.gml",true, 73, 194),
	INTERNETMCI("topologyZoo/Internetmci.gml",true, 19, 90),
	INTERNODE("topologyZoo/Internode.gml",true, 66, 156),
	INTEROUTE("topologyZoo/Interoute.gml",true, 110, 316),
	INTRANETWORK("topologyZoo/Intranetwork.gml",true, 39, 106),
	ION("topologyZoo/Ion.gml",true, 125, 300),
	IOWASTATEWIDEFIBERMAP("topologyZoo/IowaStatewideFiberMap.gml",true, 33, 82),
	IRIS("topologyZoo/Iris.gml",true, 51, 128),
	ISTAR("topologyZoo/Istar.gml",true, 23, 46),
	ITNET("topologyZoo/Itnet.gml",true, 11, 20),
	JANETBACKBONE("topologyZoo/Janetbackbone.gml",true, 29, 90),
	JANETEXTERNAL("topologyZoo/JanetExternal.gml",false, 12, 20),
	JANETLENSE("topologyZoo/Janetlense.gml",true, 20, 80),
	JGN2PLUS("topologyZoo/Jgn2Plus.gml",true, 18, 34),
	KAREN("topologyZoo/Karen.gml",true, 25, 60),
	KDL("topologyZoo/Kdl.gml",true, 754, 1798),
	KENTMANAPR2007("topologyZoo/KentmanApr2007.gml",false, 23, 48),
	KENTMANAUG2005("topologyZoo/KentmanAug2005.gml",false, 28, 58),
	KENTMANFEB2008("topologyZoo/KentmanFeb2008.gml",true, 26, 56),
	KENTMANJAN2011("topologyZoo/KentmanJan2011.gml",true, 38, 78),
	KENTMANJUL2005("topologyZoo/KentmanJul2005.gml",true, 16, 34),
	KREONET("topologyZoo/Kreonet.gml",true, 13, 24),
	LAMBDANET("topologyZoo/LambdaNet.gml",true, 42, 92),
	LATNET("topologyZoo/Latnet.gml",true, 69, 148),
	LAYER42("topologyZoo/Layer42.gml",true, 6, 14),
	LITNET("topologyZoo/Litnet.gml",true, 43, 86),
	MARNET("topologyZoo/Marnet.gml",true, 20, 54),
	MARWAN("topologyZoo/Marwan.gml",true, 16, 36),
	MISSOURI("topologyZoo/Missouri.gml",true, 67, 166),
	MREN("topologyZoo/Mren.gml",true, 6, 10),
	MYREN("topologyZoo/Myren.gml",true, 37, 80),
	NAPNET("topologyZoo/Napnet.gml",true, 6, 14),
	NAVIGATA("topologyZoo/Navigata.gml",true, 13, 34),
	NETRAIL("topologyZoo/Netrail.gml",true, 7, 20),
	NETWORKUSA("topologyZoo/NetworkUsa.gml",true, 35, 78),
	NEXTGEN("topologyZoo/Nextgen.gml",true, 17, 40),
	NIIF("topologyZoo/Niif.gml",true, 36, 82),
	NOEL("topologyZoo/Noel.gml",true, 19, 50),
	NORDU1989("topologyZoo/Nordu1989.gml",true, 7, 12),
	NORDU1997("topologyZoo/Nordu1997.gml",true, 14, 26),
	NORDU2005("topologyZoo/Nordu2005.gml",true, 9, 20),
	NORDU2010("topologyZoo/Nordu2010.gml",false, 18, 34),
	NSFCNET("topologyZoo/Nsfcnet.gml",false, 10, 20),
	NSFNET("topologyZoo/Nsfnet.gml",true, 13, 30),
	NTELOS("topologyZoo/Ntelos.gml",false, 48, 122),
	NTT("topologyZoo/Ntt.gml",false, 47, 432),
	OTEGLOBE("topologyZoo/Oteglobe.gml",false, 93, 212),
	OXFORD("topologyZoo/Oxford.gml",true, 20, 52),
	PACIFICWAVE("topologyZoo/Pacificwave.gml",true, 18, 54),
	PACKETEXCHANGE("topologyZoo/Packetexchange.gml",true, 21, 54),
	PADI("topologyZoo/Padi.gml",false, 15, 12),
	PALMETTO("topologyZoo/Palmetto.gml",true, 45, 140),
	PEER1("topologyZoo/Peer1.gml",true, 16, 40),
	PERN("topologyZoo/Pern.gml",true, 127, 258),
	PIONIERL1("topologyZoo/PionierL1.gml",true, 36, 82),
	PIONIERL3("topologyZoo/PionierL3.gml",true, 38, 104),
	PSINET("topologyZoo/Psinet.gml",true, 24, 50),
	QUEST("topologyZoo/Quest.gml",true, 20, 62),
	REDBESTEL("topologyZoo/RedBestel.gml",true, 84, 202),
	REDIRIS("topologyZoo/Rediris.gml",true, 19, 64),
	RENAM("topologyZoo/Renam.gml",true, 5, 8),
	RENATER1999("topologyZoo/Renater1999.gml",true, 24, 46),
	RENATER2001("topologyZoo/Renater2001.gml",true, 24, 54),
	RENATER2004("topologyZoo/Renater2004.gml",true, 30, 72),
	RENATER2006("topologyZoo/Renater2006.gml",true, 33, 86),
	RENATER2008("topologyZoo/Renater2008.gml",true, 33, 86),
	RENATER2010("topologyZoo/Renater2010.gml",true, 43, 112),
	RESTENA("topologyZoo/Restena.gml",true, 19, 42),
	REUNA("topologyZoo/Reuna.gml",true, 37, 72),
	RHNET("topologyZoo/Rhnet.gml",true, 16, 36),
	RNP("topologyZoo/Rnp.gml",true, 31, 68),
	ROEDUNET("topologyZoo/Roedunet.gml",true, 42, 100),
	ROEDUNETFIBRE("topologyZoo/RoedunetFibre.gml",true, 48, 104),
	SAGO("topologyZoo/Sago.gml",true, 18, 34),
	SANET("topologyZoo/Sanet.gml",true, 43, 90),
	SANREN("topologyZoo/Sanren.gml",true, 7, 14),
	SAVVIS("topologyZoo/Savvis.gml",true, 19, 40),
	SHENTEL("topologyZoo/Shentel.gml",true, 28, 70),
	SINET("topologyZoo/Sinet.gml",true, 74, 152),
	SINGAREN("topologyZoo/Singaren.gml",true, 11, 20),
	SPIRALIGHT("topologyZoo/Spiralight.gml",true, 15, 32),
	SPRINT("topologyZoo/Sprint.gml",true, 11, 36),
	SUNET("topologyZoo/Sunet.gml",true, 26, 98),
	SURFNET("topologyZoo/Surfnet.gml",true, 50, 146),
	SWITCH("topologyZoo/Switch.gml",true, 74, 184),
	SWITCHL3("topologyZoo/SwitchL3.gml",true, 42, 126),
	SYRINGA("topologyZoo/Syringa.gml",true, 74, 148),
	TATANLD("topologyZoo/TataNld.gml",true, 145, 388),
	TELCOVE("topologyZoo/Telcove.gml",false, 73, 140),
	TELECOMSERBIA("topologyZoo/Telecomserbia.gml",true, 6, 12),
	TINET("topologyZoo/Tinet.gml",true, 53, 178),
	TLEX("topologyZoo/TLex.gml",true, 12, 32),
	TW("topologyZoo/Tw.gml",false, 76, 236),
	TWAREN("topologyZoo/Twaren.gml",true, 20, 40),
	ULAKNET("topologyZoo/Ulaknet.gml",true, 82, 164),
	UNIC("topologyZoo/UniC.gml",true, 25, 58),
	UNINET("topologyZoo/Uninet.gml",true, 13, 50),
	UNINETT2010("topologyZoo/Uninett2010.gml",true, 74, 202),
	UNINETT2011("topologyZoo/Uninett2011.gml",true, 69, 196),
	URAN("topologyZoo/Uran.gml",true, 24, 48),
	USCARRIER("topologyZoo/UsCarrier.gml",true, 158, 378),
	USSIGNAL("topologyZoo/UsSignal.gml",false, 63, 158),
	UUNET("topologyZoo/Uunet.gml",true, 49, 168),
	VINAREN("topologyZoo/Vinaren.gml",true, 25, 52),
	VISIONNET("topologyZoo/VisionNet.gml",true, 24, 46),
	VTLWAVENET2008("topologyZoo/VtlWavenet2008.gml",true, 88, 184),
	VTLWAVENET2011("topologyZoo/VtlWavenet2011.gml",true, 92, 192),
	WIDEJPN("topologyZoo/WideJpn.gml",true, 30, 66),
	XEEX("topologyZoo/Xeex.gml",true, 24, 68),
	XSPEDIUS("topologyZoo/Xspedius.gml",true, 34, 98),
	YORK("topologyZoo/York.gml",true, 23, 48),
	ZAMREN("topologyZoo/Zamren.gml",false, 36, 68),
	NO ("",true, 1, 1);

	private String gmlFilePath;
	private boolean connected;
	private int nNodes;
	private int nEdges;

	TopologyZoo(String gmlFilePath, boolean connected, int nNodes, int nEdges) {
		this.gmlFilePath = gmlFilePath;
		this.connected = connected;
		this.nNodes = nNodes;
		this.nEdges = nEdges;
	}

	public File getTopologyFile() {
		if(gmlFilePath == null)
			return null;
		return new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(gmlFilePath)).getFile());
	}

	public String getTopologyContent() throws IOException {
		if(gmlFilePath == null)
			return null;
		StringWriter writer = new StringWriter();
		IOUtils.copy(getInputStream(), writer);
		return writer.toString();
	}

	public InputStream getInputStream() {
		if(gmlFilePath == null)
			return null;
		if(gmlFilePath.length() > 0 && gmlFilePath.charAt(0) != '/')
			gmlFilePath = "/" + gmlFilePath;
		return this.getClass().getResourceAsStream(gmlFilePath);
	}

	public boolean isTopologyConnected() {
		return connected;
	}

	public int getNumberOfNodes() {
		return nNodes;
	}

	public int getNumberOfEdges() {
		return nEdges;
	}
}
