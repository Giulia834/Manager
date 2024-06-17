# Nome dos arquivos Java (sem a extensão .java)
JAVA_FILES := CheckBoxPanel CustomButton EmojiRenderer Game GameDialog GameList GameManager GameManagerGUI GameTable RoundTextField Tag TagDialog TagManager

# Diretórios
SRC_DIR := src
TEST_DIR := test
BIN_DIR := bin

# Compilador Java
JAVAC := javac

# Flags do compilador
JAVAC_FLAGS := -d $(BIN_DIR) -sourcepath $(SRC_DIR)

# Arquivos .class gerados
CLASS_FILES := $(addprefix $(BIN_DIR)/, $(addsuffix .class, $(JAVA_FILES)))

# Alvos
all: $(CLASS_FILES)

# Compilação dos arquivos Java
$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	$(JAVAC) $(JAVAC_FLAGS) $<

run:
	java -cp bin GameManagerGUI

# Testes
test: all
	$(JAVAC) -cp $(BIN_DIR) -d $(BIN_DIR) $(TEST_DIR)/*.java
	java -cp $(BIN_DIR) org.junit.runner.JUnitCore GameListTest GameManagerTest TagManagerTest

# Limpeza
clean:
	rm -rf $(BIN_DIR)

.PHONY: all test clean