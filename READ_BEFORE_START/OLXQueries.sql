
CREATE DATABASE OLX;
GO
USE [OLX]
GO


CREATE TABLE [dbo].[Incorrect_SouthAfrican_Telephones](
	[ID] [int] NOT NULL PRIMARY KEY,
	[TELEPHONE] [varchar](50) NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[SouthAfrican_Telephones](
	[ID] [int] NOT NULL PRIMARY KEY,
	[TELEPHONE] [varchar](50) NOT NULL,
	[ISFIXED] [varchar](100) NULL
) ON [PRIMARY]
GO



